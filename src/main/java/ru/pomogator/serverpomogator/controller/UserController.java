package ru.pomogator.serverpomogator.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.pomogator.serverpomogator.domain.dto.auth.AuthenticationResponse;
import ru.pomogator.serverpomogator.domain.dto.auth.UserRequest;
import ru.pomogator.serverpomogator.servise.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/info")
    public AuthenticationResponse updateuserInfo(@RequestBody @Validated(UserRequest.UserInfo.class) UserRequest request) {
        return userService.updateUserInfo(request);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody @Validated(UserRequest.UserChangePassword.class) UserRequest req, HttpServletRequest request) {
        return userService.changePassword(req, request);
    }

    @PostMapping("/for-got-password")
    public ResponseEntity<Void> forGotPassword(@RequestBody @Validated(UserRequest.ForGotPassword.class)  UserRequest req) {
        return userService.forGotPassword(req);
    }

    @PostMapping("/avatar/add")
    public ResponseEntity<?> userAvatarAdd(@Validated(UserRequest.UserAvatar.class) @ModelAttribute UserRequest req) {
        return userService.addAvatar(req);
    }

    @GetMapping("/avatar/remove")
    public ResponseEntity<?> userAvatarRemove(HttpServletRequest request) {
        return userService.removeAvatar(request);
    }

    @GetMapping("/material")
    public ResponseEntity<?> userMaterial(Long id,  @RequestParam(required = false) List<String> tags) {
        return userService.userMaterial(id, tags);
    }
}


