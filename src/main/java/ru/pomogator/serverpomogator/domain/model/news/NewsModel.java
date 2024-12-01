package ru.pomogator.serverpomogator.domain.model.news;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import ru.pomogator.serverpomogator.domain.model.base.BaseEntity;
import ru.pomogator.serverpomogator.domain.model.file.FileModel;
import ru.pomogator.serverpomogator.domain.model.user.User;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NEWS")
public class NewsModel extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "annotation", nullable = false)
    private String annotation;

    @Lob
    @Column(name = "article", nullable = false, columnDefinition = "TEXT")
    private String article;

    @Column(name = "shows", nullable = false)
    private int shows;

    @Column(name = "published", nullable = false)
    @ColumnDefault("false")
    private boolean published;

    @Column(name = "likes", nullable = false)
    private int likes;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<TagsModel> tags;

    @Column(name = "video")
    private String video;

    @Column(name = "link_to_source")
    @ColumnDefault("null")
    private String link_to_source;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryModel category;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private FileModel file;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<FavoriteModel> favorite;
}

