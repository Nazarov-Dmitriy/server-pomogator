package ru.pomogator.serverpomogator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pomogator.serverpomogator.domain.dto.auth.*;
import ru.pomogator.serverpomogator.domain.model.User;
import ru.pomogator.serverpomogator.servise.user.AuthenticationService;
import ru.pomogator.serverpomogator.servise.user.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }


    @PostMapping("/user-info")
    public User registerInfo(@RequestBody @Valid AuthenticationRequest request) {
        System.out.println(request);
        return userService.registerInfo(request);
    }

    @PostMapping("/auto-login")
    public void registerInfo(@RequestBody  String token) {
        System.out.println(token);
//        return authenticationService.autoLogin(token);
    }
}


