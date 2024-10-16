package ru.pomogator.serverpomogator.servise.user;

import lombok.RequiredArgsConstructor;
import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pomogator.serverpomogator.domain.dto.auth.AuthenticationResponse;
import ru.pomogator.serverpomogator.domain.dto.auth.UserRequest;
import ru.pomogator.serverpomogator.domain.dto.auth.UserResponse;
import ru.pomogator.serverpomogator.domain.mapper.UserMapper;
import ru.pomogator.serverpomogator.domain.model.user.Role;
import ru.pomogator.serverpomogator.domain.model.user.User;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.repository.user.UserRepository;
import ru.pomogator.serverpomogator.security.CustomerUserDetailsService;
import ru.pomogator.serverpomogator.security.JwtUser;
import ru.pomogator.serverpomogator.servise.mail.EmailService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomerUserDetailsService customerUserDetailsService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Autowired
    EmailService emailService;

    public AuthenticationResponse signUp(UserRequest request) {
        var user = User.builder().email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(Role.ROLE_USER).build();
        userService.create(user);
        var jwtUser = new JwtUser(user);
        var jwt = jwtService.generateToken(jwtUser);
        emailService.sendMessageRegisterUser(user, request.getPassword());
        return new AuthenticationResponse(jwt, userMapper.toUserResponse(user));
    }

    public AuthenticationResponse signIn(UserRequest request) throws BadRequest {
        var user = customerUserDetailsService.loadUserByUsername(request.getEmail());
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new BadRequest("Bad password");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var jwt = jwtService.generateToken(user);

        var responseUser = userMapper.toUserResponse(user.getUser());

        if (user.getUser().getAvatar() != null) {
            responseUser.setAvatar(user.getUser().getAvatar().getPath());
        }
        return new AuthenticationResponse(jwt, responseUser);
    }

    public UserResponse registerInfo(UserRequest request) {
        Optional<User> user = userService.findByEmail(request.getEmail());
        if (user.isPresent()) {
            userMapper.updateUserFromDto(request, user.get());
            userRepository.save(user.get());
            return userMapper.toUserResponse(user.get());
        } else {
            throw new BadRequest("Bad email");
        }
    }

    public UserResponse autoLogin(UserRequest token) {
        var email = jwtService.extractUserName(token.getToken());
        var user = customerUserDetailsService.loadUserByUsername(email);
        var responseUser = userMapper.toUserResponse(user.getUser());

        if (user.getUser().getAvatar() != null) {
            responseUser.setAvatar(user.getUser().getAvatar().getPath());
        }

        return responseUser;
    }
}



