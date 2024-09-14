package ru.pomogator.serverpomogator.servise.user;

import lombok.RequiredArgsConstructor;
import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pomogator.serverpomogator.domain.dto.auth.JwtAuthenticationResponse;
import ru.pomogator.serverpomogator.domain.dto.auth.SignInRequest;
import ru.pomogator.serverpomogator.domain.dto.auth.SignUpRequest;
import ru.pomogator.serverpomogator.domain.dto.auth.UserResponse;
import ru.pomogator.serverpomogator.domain.mapper.UserMapper;
import ru.pomogator.serverpomogator.domain.model.Role;
import ru.pomogator.serverpomogator.domain.model.User;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.security.CustomerUserDetailsService;
import ru.pomogator.serverpomogator.security.JwtUser;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomerUserDetailsService customerUserDetailsService;
    private final UserMapper userMapper;


    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

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
        return new JwtAuthenticationResponse(jwt, userMapper.toUserResponse(user));
//        return new JwtAuthenticationResponse(jwt,   userResponse);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) throws BadRequest {
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

//        return new JwtAuthenticationResponse(jwt, userMapper.toUserResponse(user.getUser()));
        return new JwtAuthenticationResponse(jwt,  userResponse);
    }

    public User autoLogin(String login) {
        System.out.println(login);
//        var уьфшд = jwtService.extractUsername(jwt);

        return new User();
    }
}
