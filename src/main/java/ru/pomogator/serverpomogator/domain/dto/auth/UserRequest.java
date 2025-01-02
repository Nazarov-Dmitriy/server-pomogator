package ru.pomogator.serverpomogator.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.domain.model.user.Role;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов", groups = {SignIn.class, SignUpOne.class, UserInfo.class, ForGotPassword.class})
    @NotBlank(message = "Поле не должно быть пустым", groups = {SignIn.class, SignUpOne.class, UserInfo.class, ForGotPassword.class})
    @Email(message = "Email адрес должен быть в формате user@example.ru", groups = {SignIn.class, SignUpOne.class, UserInfo.class, ForGotPassword.class})
    private String email;

    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8", groups = {SignIn.class, SignUpOne.class, UserChangePassword.class})
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;

    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8", groups = {UserChangePassword.class})
    @NotBlank(message = "Поле не должно быть пустым", groups = {UserChangePassword.class})
    private String new_password;

    @NotNull(message = "Поле не должно быть пустым", groups = {ChangeRole.class})
    private Role role;

    @NotEmpty(message = "Поле не должно быть пустым", groups = {UserInfo.class})
    private String name;

    @NotEmpty(message = "Поле не должно быть пустым", groups = {UserInfo.class})
    private String surname;

    private String patronymic;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date_birth;

    @NotEmpty(message = "Поле не должно быть пустым", groups = {UserInfo.class})
    private String position;

    @NotEmpty(message = "Поле не должно быть пустым", groups = {UserInfo.class})
    private String place_work;

    private String rank_user;

    private String phone;

    private String token;

    private Boolean completed_profile;

    private String current_email;

    @NotNull(message = "Поле не должно быть пустым", groups = {ChangeRole.class})
    private Long user_id;

    @NotNull(groups = {UserAvatar.class})
    private MultipartFile avatar;

    /**
     * Группа проверок для авторизации
     */
    public interface SignIn {
    }

    /**
     * Группа проверок для регистрации
     */
    public interface SignUpOne {
    }

    /**
     * Группа проверок для регистрации информации о пользователи
     */
    public interface UserInfo {
    }

    /**
     * Группа проверок для аватара пользователя
     */
    public interface UserAvatar {
    }

    /**
     * Группа проверок для смены пароля
     */
    public interface UserChangePassword {
    }

    /**
     * Группа проверок для восстановления пароля
     */
    public interface ForGotPassword {
    }

    /**
     * Группа проверок для восстановления пароля
     */
    public interface ChangeRole {
    }
}



