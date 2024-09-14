package ru.pomogator.serverpomogator.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.pomogator.serverpomogator.domain.model.Role;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Size(min = 1, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов", groups = {SignIn.class, SignUpOne.class, SignUpTwo.class})
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Size(min = 5, max = 255, message = "Длина пароля должна быть от 8", groups = {SignIn.class, SignUpOne.class})
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;

    private Role role;

    @NotEmpty(message = "Имя не должно быть пустым", groups = {SignUpTwo.class})
    private String name;

    @NotEmpty(message = "Фамилия не должно быть пустой", groups = {SignUpTwo.class})
    private String surname;

    private String patronymic;

    @JsonFormat(pattern="yyyy-mm-dd")
    private Date date_birth;

    @NotEmpty(message = "Должность не должно быть пустой", groups = {SignUpTwo.class})
    private String position;

    @NotEmpty(message = "Место работы не должно быть пустым", groups = {SignUpTwo.class})
    private String place_work;

    private String rank;

    private String phone;

    private String token;

    /**
     * группа проверок для авторизации
     */
    public interface SignIn {
    }

    /**
     * группа проверок для регистрации
     */
    public interface SignUpOne {
    }

    /**
     * группа проверок для регистрации информации о пользователи
     */
    public interface SignUpTwo {
    }
}



