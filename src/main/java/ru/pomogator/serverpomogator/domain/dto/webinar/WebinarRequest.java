package ru.pomogator.serverpomogator.domain.dto.webinar;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;
import ru.pomogator.serverpomogator.domain.model.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Value
public class WebinarRequest {
    Long id;
    @NotBlank(message = "Поле не должно быть пустым")
    String title;
    @NotBlank(message = "Поле не должно быть пустым")
    String annotation;
    @NotEmpty(message = "Поле не должно быть пустым")
    List<TagsModel> tags;
    @NotEmpty(message = "Поле не должно быть пустым")
    String video;
    @NotNull(message = "Поле не должно быть пустым")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime date_translation;
    User author;
}
