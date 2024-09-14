package ru.pomogator.serverpomogator.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import ru.pomogator.serverpomogator.domain.model.Role;

import java.util.Date;

@Data
@AllArgsConstructor
@Getter
@ToString
public class UserResponse2 {
    private Long id;
    private String email;
    private Role role;
    private String name;
    private String surname;
    private String patronymic;
    private Date date_birth;
    private String position;
    private String place_work;
    private String rank;
    private String phone;

    public UserResponse2(Long id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}

//    @PostMapping("/sign-in")
//    public AuthenticationResponse signIn(@Validated(AuthenticationRequest.SiginIn.class) @RequestBody AuthenticationRequest request) {
////        return authenticationService.signIn(request);
//        return new AuthenticationResponse();
//    }

