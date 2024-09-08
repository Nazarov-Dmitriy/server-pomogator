package ru.pomogator.serverpomogator.servise.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pomogator.serverpomogator.domain.dto.auth.JwtAuthenticationResponse;
import ru.pomogator.serverpomogator.domain.dto.auth.SignInRequest;
import ru.pomogator.serverpomogator.domain.dto.auth.SignUpRequest;
import ru.pomogator.serverpomogator.domain.model.Role;
import ru.pomogator.serverpomogator.domain.model.User;
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
        var jwtUser = new JwtUser(user.getId(),  user.getEmail(), user.getPassword(), user.getRole());
        var jwt = jwtService.generateToken(jwtUser);
        return new JwtAuthenticationResponse(jwt, user);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = customerUserDetailsService.loadUserByUsername(request.getUsername());


        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
