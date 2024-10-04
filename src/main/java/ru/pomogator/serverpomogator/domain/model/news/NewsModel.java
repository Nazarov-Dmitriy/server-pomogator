package ru.pomogator.serverpomogator.domain.model.news;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import ru.pomogator.serverpomogator.domain.model.BaseEntity;
import ru.pomogator.serverpomogator.domain.model.FileModel;
import ru.pomogator.serverpomogator.domain.model.User;

import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "NEWS")
public class NewsModel extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "annotation", nullable = false)
    private String annotation;

    @Lob
    @Column(name = "article", nullable = false, columnDefinition = "TEXT")
    private String article;

    @Column(name = "shows", nullable = false)
    private int shows;

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private FileModel file;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @OneToMany(mappedBy = "news" ,fetch = FetchType.LAZY)
    List<FavoriteModel> favorite;
}

