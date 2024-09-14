package ru.pomogator.serverpomogator.domain.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Size(min = 1, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов" , groups = {SiginIn.class})
    @NotBlank(message = "Адрес электронной почты не может быть пустыми" )
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Size(min = 5, max = 255, message = "Длина пароля должна быть от 8", groups = {SiginIn.class})
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    private String patronymic;

    @Size(min = 10, max = 10, message = "Дата рождения должна быть в формате 01.01.1990")
    private String date_birth;

    @NotEmpty
    private String position;

    @NotEmpty
    private String place_work;

    private String rank;

    private String phone;

    /**
     * группа проверок для авторизации
     */
    public interface SiginIn {
    }

    /**
     * группа проверок для регистрации
     */
    public interface SiginUp {
    }
}



