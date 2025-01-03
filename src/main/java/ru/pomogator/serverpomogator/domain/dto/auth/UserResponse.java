package ru.pomogator.serverpomogator.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.pomogator.serverpomogator.domain.model.user.Role;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
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
    private String rank_user;
    private String phone;
    private Boolean completed_profile;
    private String avatar;
}
