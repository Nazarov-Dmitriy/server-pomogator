package ru.pomogator.serverpomogator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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

    @PostMapping("/sign-up")
    public AuthenticationResponse signUp(@RequestBody @Validated(AuthenticationRequest.SignUpOne.class) AuthenticationRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public AuthenticationResponse signIn(@RequestBody @Validated(AuthenticationRequest.SignIn.class) AuthenticationRequest request) {
        return authenticationService.signIn(request);
    }


    @PostMapping("/user-info")
    public UserResponse registerInfo(@RequestBody  @Validated(AuthenticationRequest.SignUpTwo.class) AuthenticationRequest request) {
        System.out.println(request);
        return authenticationService.registerInfo(request);
    }

    @PostMapping("/auto-login")
    public UserResponse autoLogin(@RequestBody  AuthenticationRequest token) {
        return authenticationService.autoLogin(token);
    }
}


