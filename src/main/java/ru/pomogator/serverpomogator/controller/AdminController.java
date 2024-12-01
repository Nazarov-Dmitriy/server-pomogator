package ru.pomogator.serverpomogator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.pomogator.serverpomogator.domain.dto.auth.AuthenticationResponse;
import ru.pomogator.serverpomogator.domain.dto.auth.UserRequest;
import ru.pomogator.serverpomogator.domain.dto.auth.UserResponse;
import ru.pomogator.serverpomogator.servise.user.AuthenticationService;
import ru.pomogator.serverpomogator.servise.user.UserService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/user-list")
    public ResponseEntity<?>  getUserList() {
        return userService.getUserList();
    }

    @PutMapping("/change-role")
    public ResponseEntity<?>  setUserRole(@RequestBody @Validated(UserRequest.ChangeRole.class) UserRequest body) {

        return userService.setUserRole(body);
    }

    @GetMapping("/user-remove/{id}")
    public ResponseEntity<?>  removeUser(@PathVariable Long id) {
        return userService.removeUser(id);
    }
}


