package ru.pomogator.serverpomogator.domain.model.file;

import jakarta.persistence.*;
import lombok.*;
import ru.pomogator.serverpomogator.domain.model.news.NewsModel;
import ru.pomogator.serverpomogator.domain.model.user.User;
import ru.pomogator.serverpomogator.domain.model.webinar.WebinarModel;

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

    @OneToOne( mappedBy = "file" , fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private NewsModel news;

    @OneToOne( mappedBy = "preview_img" , fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private WebinarModel webinar;

    @OneToOne( mappedBy = "avatar" , fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id", referencedColumnName = "id")
    private User user;
}

