package ru.pomogator.serverpomogator.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.pomogator.serverpomogator.dto.UserDto;
import ru.pomogator.serverpomogator.servise.UserServise;

import java.util.Map;

@RestController
public class UserController {
    private final UserServise userServise;

    public UserController(UserServise userServise) {
        this.userServise = userServise;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> login(@Validated(UserDto.Login.class)  @RequestBody UserDto user, HttpServletRequest request) {
        System.out.println(user);
        return userServise.login(user, request);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Validated(UserDto.Register.class) UserDto userDto) {
                return userServise.rigisterUser(userDto);
    }
}
