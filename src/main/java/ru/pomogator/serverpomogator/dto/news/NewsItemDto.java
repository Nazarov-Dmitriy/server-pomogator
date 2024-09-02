package ru.pomogator.serverpomogator.dto.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@ToString
public class NewsItemDto extends NewsDto {

    private String image;

    private Integer shows;

    private Integer likes;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date date_publication;

    @Builder
    public NewsItemDto(Long id, @NotBlank(groups = {addNews.class}) String title,
                       @NotBlank(groups = {addNews.class}) String subtitle,
                       @NotBlank(groups = {addNews.class}) String article,
                       @NotNull(groups = {addNews.class}) Long category,
                       @NotBlank Date date_publication,
                       @NotEmpty(groups = {addNews.class}) List<Integer> tags,
                       String image,
                       Integer shows,
                       Integer likes) {
        super(id, title, subtitle, article, category, date_publication, tags);
        this.image = image;
        this.shows = shows;
        this.likes = likes;
        this.date_publication = date_publication;
    }
}
