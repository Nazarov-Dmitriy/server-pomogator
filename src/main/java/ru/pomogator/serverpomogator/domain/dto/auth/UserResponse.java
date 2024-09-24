package ru.pomogator.serverpomogator.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pomogator.serverpomogator.domain.model.Role;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private Role role;
    private String name;
    private String surname;
    private String patronymic;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date_birth;
    private String position;
    private String place_work;
    private String rank;
    private String phone;
    private Boolean completed_profile;
    private String avatar;
}
