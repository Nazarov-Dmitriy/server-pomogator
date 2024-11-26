package ru.pomogator.serverpomogator.domain.dto.reviews;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ru.pomogator.serverpomogator.domain.model.reviews.ReviewsModel}
 */
public record ReviewsModelDto(Long id,     @JsonFormat(pattern = "dd-MM-yyyy") LocalDate date, String description, String author,
                              String filePath) implements Serializable {
}