package ru.pomogator.serverpomogator.domain.model;

import jakarta.persistence.*;
import lombok.*;
import ru.pomogator.serverpomogator.domain.model.news.NewsModel;

@ToString
@Data
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FILES")
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false , columnDefinition="BLOB")
    private String path;

    @Column(nullable = false)
    private long size;

    @OneToOne( mappedBy = "file" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "news_id", referencedColumnName = "id")
    private NewsModel news;
}

