package ru.pomogator.serverpomogator.domain.dto.material;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.pomogator.serverpomogator.domain.model.user.Role;
import ru.pomogator.serverpomogator.domain.model.user.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link ru.pomogator.serverpomogator.domain.model.news.NewsModel}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MaterialResponse implements Serializable {
    private Long id;
    private String title;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDateTime createdAt;
    private int shows;
    private int likes;
    private List<Long> tags;
    private String video;
    private String file;
    private String type;
    private String preview_img;
    private Boolean published;
}