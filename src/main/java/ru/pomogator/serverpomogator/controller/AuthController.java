package ru.pomogator.serverpomogator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.pomogator.serverpomogator.domain.dto.auth.AuthenticationResponse;
import ru.pomogator.serverpomogator.domain.dto.auth.UserRequest;
import ru.pomogator.serverpomogator.domain.dto.auth.UserResponse;
import ru.pomogator.serverpomogator.servise.user.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public AuthenticationResponse signUp(@RequestBody @Validated(UserRequest.SignUpOne.class) UserRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public AuthenticationResponse signIn(@RequestBody @Validated(UserRequest.SignIn.class) UserRequest request) {
        return authenticationService.signIn(request);
    }


    @PostMapping("/user-info")
    public UserResponse registerInfo(@RequestBody @Validated(UserRequest.UserInfo.class) UserRequest request) {
        return authenticationService.registerInfo(request);
    }

    @PostMapping("/auto-login")
    public UserResponse autoLogin(@RequestBody UserRequest token) {
        return authenticationService.autoLogin(token);
    }
}


