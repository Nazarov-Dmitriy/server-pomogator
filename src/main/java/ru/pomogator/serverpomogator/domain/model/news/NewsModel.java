package ru.pomogator.serverpomogator.domain.model.news;

import jakarta.persistence.*;
import lombok.*;
import ru.pomogator.serverpomogator.domain.model.BaseEntity;
import ru.pomogator.serverpomogator.domain.model.FileModel;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
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
    @Column(name = "tags")
    private List<TagsModel> tags;

    @Column(name = "video")
    private String video;

    @Column(name = "link_to_source")
    private String link_to_source;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryModel category;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private FileModel file;

}

