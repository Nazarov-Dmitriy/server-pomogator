package ru.pomogator.serverpomogator.domain.dto.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import ru.pomogator.serverpomogator.domain.model.news.CategoryModel;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link ru.pomogator.serverpomogator.domain.model.news.NewsModel}
 */
@Value
public class NewsRequest implements Serializable {
    @NotBlank
    String title;
    @NotBlank
    String annotation;
    @NotBlank
    String article;
    @NotNull
    List<TagsModel> tags;
    String video;
    String link_to_source;
    @NotNull
    CategoryModel category;
}