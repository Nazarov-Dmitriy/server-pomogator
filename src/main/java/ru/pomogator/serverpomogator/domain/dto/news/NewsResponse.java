package ru.pomogator.serverpomogator.domain.dto.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link ru.pomogator.serverpomogator.domain.model.news.NewsModel}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class NewsResponse implements Serializable {
    private Long id;
    private String title;
    private String annotation;
    private String article;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDateTime updatedAt;
    private int shows;
    private int likes;
    private List<Long> tags;
    private String video;
    private String link_to_source;
    private Long category;
    private String file;
}