package ru.pomogator.serverpomogator.domain.model.webinar;

import jakarta.persistence.*;
import lombok.*;
import ru.pomogator.serverpomogator.domain.model.base.BaseEntity;
import ru.pomogator.serverpomogator.domain.model.file.FileModel;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;
import ru.pomogator.serverpomogator.domain.model.user.User;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "webinar")
public class WebinarModel extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "annotation", nullable = false)
    private String annotation;

    @Column(name = "shows", nullable = false)
    private int shows;

    @Column(name = "likes", nullable = false)
    private int likes;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<TagsModel> tags;

    @Column(name = "video")
    private String video;

    @Column(name = "date_translation")
    private Date date_translation;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private FileModel preview_img;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

//    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
//    List<FavoriteModel> favorite;
//
//    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
//    List<FavoriteModel> favorite;
}

