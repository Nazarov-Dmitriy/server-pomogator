package ru.pomogator.serverpomogator.domain.dto.subsribe;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubcribeDto {
    @Size(min = 1, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Поле не должно быть пустым")
    @Email(message = "Email адрес должен быть в формате user@example.ru")
    private String email;
}