package ru.pomogator.serverpomogator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@AllArgsConstructor
@Getter
@ToString
public class UserDto {
    @NotBlank(groups = {Login.class, Register.class})
    private String login;
    private String role;
    @NotBlank(groups = {Login.class, Register.class})
    private String password;
    @NotBlank(groups = {Register.class})
    private String email;
    @NotBlank(groups = {Register.class})
    private String name;
    @NotBlank(groups = {Register.class})
    private String surname;
    @NotBlank(groups = {Register.class})
    private String patronymic;

    /**
     * группа проверок для логина
     */
    public interface Login {
    }

    /**
     * группа проверок для регистрации
     */
    public interface Register {
    }
}
