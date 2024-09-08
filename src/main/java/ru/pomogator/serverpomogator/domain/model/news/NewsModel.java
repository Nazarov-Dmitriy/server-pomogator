package ru.pomogator.serverpomogator.domain.model.news;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import ru.pomogator.serverpomogator.domain.model.FileModel;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "NEWS")
public class NewsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String article;

    @Column(nullable = false)
    private Long category_id;

    @CreatedDate
    @Column()
    private Date date_publication;

    @Column(nullable = false)
    private int shows;

    @Column(nullable = false)
    private int likes;

    @Column(nullable = false)
    private String tags;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CategoryModel category;

    @OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FileModel file;
}

