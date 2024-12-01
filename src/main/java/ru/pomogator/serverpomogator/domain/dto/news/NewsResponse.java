package ru.pomogator.serverpomogator.domain.dto.news;

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
    private UserDto author;
    private String type="article";
    private Boolean published;


    /**
     * DTO for {@link User}
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class UserDto implements Serializable {
        private Long id;
        private String email;
        private Role role;
        private String name;
        private String surname;
        private String patronymic;
        private Date date_birth;
        private String position;
        private String place_work;
        private String rank;
        private String phone;
        private Boolean completed_profile;
        private String avatarPath;
    }
}