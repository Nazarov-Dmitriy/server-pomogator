package ru.pomogator.serverpomogator.domain.dto.reviews;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ru.pomogator.serverpomogator.domain.model.reviews.ReviewsModel;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ReviewsModel}
 */
public record ReviewsDto(@NotNull(message = "Поле не должно быть пустым") LocalDate date,
                         @NotBlank(message = "Поле не должно быть пустым") String description,
                         @NotBlank(message = "Поле не должно быть пустым") String author) implements Serializable {
}