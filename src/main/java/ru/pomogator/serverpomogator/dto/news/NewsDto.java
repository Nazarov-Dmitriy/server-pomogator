package ru.pomogator.serverpomogator.dto.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Getter
@ToString
public class NewsDto {
    private Long id;
    @NotBlank(groups = {addNews.class})
    private String title;
    @NotBlank(groups = {addNews.class})
    private String subtitle;
    @NotBlank(groups = {addNews.class})
    private String article;
    @NotNull(groups = {addNews.class})
    private Long category;
    @NotBlank
    private Date date_publication;

    @NotEmpty(groups = {addNews.class})
    private List<Integer> tags;

    public interface addNews {
    }
}
