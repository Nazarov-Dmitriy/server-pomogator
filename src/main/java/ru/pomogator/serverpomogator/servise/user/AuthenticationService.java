package ru.pomogator.serverpomogator.servise.user;

import lombok.RequiredArgsConstructor;
import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pomogator.serverpomogator.domain.dto.auth.*;
import ru.pomogator.serverpomogator.domain.mapper.UserMapper;
import ru.pomogator.serverpomogator.domain.model.Role;
import ru.pomogator.serverpomogator.domain.model.User;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.repository.UserRepository;
import ru.pomogator.serverpomogator.security.CustomerUserDetailsService;
import ru.pomogator.serverpomogator.security.JwtUser;

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


    public AuthenticationResponse signUp(AuthenticationRequest request) {

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        userService.create(user);
        var jwtUser = new JwtUser(user);
        var jwt = jwtService.generateToken(jwtUser);
//        var userResponse = new UserResponse(
//                user.getId(),
//                user.getEmail(),
//                user.getRole(),
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//                );

//       var a = userMapperImpl.toUserResponse(user);
        return new AuthenticationResponse(jwt, userMapper.toUserResponse(user));
//        return new JwtAuthenticationResponse(jwt,   userResponse);
    }

    public AuthenticationResponse signIn(AuthenticationRequest request) throws BadRequest {
        var user = customerUserDetailsService.loadUserByUsername(request.getEmail());
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new BadRequest("Bad password");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));
        var jwt = jwtService.generateToken(user);
        var userResponse = new UserResponse(
                user.getUser().getId(),
                user.getUser().getEmail(),
                user.getUser().getRole(),
                user.getUser().getName(),
                user.getUser().getSurname(),
                user.getUser().getPatronymic(),
                user.getUser().getDate_birth(),
                user.getUser().getPosition(),
                user.getUser().getPlace_work(),
                user.getUser().getRank(),
                user.getUser().getPhone());
        return new AuthenticationResponse(jwt, userMapper.toUserResponse(user.getUser()));
//        return new JwtAuthenticationResponse(jwt,  userResponse);
    }

    public UserResponse registerInfo(AuthenticationRequest request) {
        Optional<User> user = userService.findByEmail(request.getEmail());
        if (user.isPresent()) {
            userMapper.updateUserFromDto(request, user.get());
            userRepository.save(user.get());
        }
//        mapper.updateCustomerFromDto(dto, myCustomer);
//        repo.save(myCustomer);
//        System.out.println(login);
//        var уьфшд = jwtService.extractUsername(jwt);
        return userMapper.toUserResponse(new User());
    }

    public UserResponse autoLogin(AuthenticationRequest token) {
        var email = jwtService.extractUserName(token.getToken());
        var user = customerUserDetailsService.loadUserByUsername(email);
        return userMapper.toUserResponse(user.getUser());
    }
}
