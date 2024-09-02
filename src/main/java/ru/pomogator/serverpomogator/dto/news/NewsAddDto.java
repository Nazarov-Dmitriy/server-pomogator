package ru.pomogator.serverpomogator.dto.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class NewsAddDto extends NewsDto {
    public NewsAddDto(Long id, @NotBlank(groups = {addNews.class}) String title, @NotBlank(groups = {addNews.class}) String subtitle, @NotBlank(groups = {addNews.class}) String article, @NotNull(groups = {addNews.class}) Long category, @NotBlank Date date_publication, @NotEmpty(groups = {addNews.class}) List<Integer> tags) {
        super(id, title, subtitle, article, category, date_publication, tags);
    }

    @NotNull(groups = {addNews.class})
    private MultipartFile file;
}
