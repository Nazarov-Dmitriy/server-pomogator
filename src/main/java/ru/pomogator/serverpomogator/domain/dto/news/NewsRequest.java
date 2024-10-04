package ru.pomogator.serverpomogator.domain.dto.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import ru.pomogator.serverpomogator.domain.model.User;
import ru.pomogator.serverpomogator.domain.model.news.CategoryModel;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link ru.pomogator.serverpomogator.domain.model.news.NewsModel}
 */
@Value
public class NewsRequest implements Serializable {
    Long id;
    @NotBlank(message = "Поле не должно быть пустым")
    String title;
    @NotBlank(message = "Поле не должно быть пустым")
    String annotation;
    @NotBlank(message = "Поле не должно быть пустым")
    String article;
    @NotEmpty(message = "Поле не должно быть пустым")
    List<TagsModel> tags;
    String video;
    String link_to_source;
    @NotNull(message = "Поле не должно быть пустым")
    CategoryModel category;
    @NotNull
    User author;
}
