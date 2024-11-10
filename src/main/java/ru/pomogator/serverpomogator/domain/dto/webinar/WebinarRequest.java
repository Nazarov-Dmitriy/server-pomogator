package ru.pomogator.serverpomogator.domain.dto.webinar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;
import ru.pomogator.serverpomogator.domain.model.user.User;

import java.util.Date;
import java.util.List;


@Data
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
    Date date_translation;
    User author;
}
