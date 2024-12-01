package ru.pomogator.serverpomogator.domain.dto.webinar;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.pomogator.serverpomogator.domain.dto.news.NewsResponse;
import ru.pomogator.serverpomogator.domain.model.webinar.Status;
import ru.pomogator.serverpomogator.domain.model.webinar.WebinarModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link WebinarModel}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WebinarResponse implements Serializable {
    private Long id;
    private String title;
    private String annotation;
    private int shows;
    private int likes;
    private List<Long> tags;
    private String video;
    private Date date_translation;
    private String preview_img;
    private NewsResponse.UserDto author;
    private Status status;
    private Boolean published;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDateTime updatedAt;
    private String type = "webinar";
}
