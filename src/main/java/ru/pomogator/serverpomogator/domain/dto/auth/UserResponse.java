package ru.pomogator.serverpomogator.domain.dto.auth;//package ru.pomogator.serverpomogator.domain.dto.auth;
//
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import ru.pomogator.serverpomogator.domain.model.Role;
//
//import java.util.Date;
//
//@Data
//@RequiredArgsConstructor
//public class UserResponse {
//    private Long id;
//    private String email;
//    private Role role;
//    private String name;
//    private String surname;
//    private String patronymic;
//    private Date date_birth;
//    private String position;
//    private String place_work;
//    private String rank;
//    private String phone;
//}
//
//

import ru.pomogator.serverpomogator.domain.model.Role;

import java.util.Date;

public record UserResponse(
        Long id,
     String email,
     Role role,
     String name,
     String surname,
     String patronymic,
     Date date_birth,
     String position,
     String place_work,
     String rank,
     String phone) {
}