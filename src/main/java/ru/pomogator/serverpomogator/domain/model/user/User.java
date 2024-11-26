package ru.pomogator.serverpomogator.domain.model.user;

import jakarta.persistence.*;
import lombok.*;
import ru.pomogator.serverpomogator.domain.model.file.FileModel;
import ru.pomogator.serverpomogator.domain.model.news.FavoriteModel;
import ru.pomogator.serverpomogator.domain.model.news.NewsModel;
import ru.pomogator.serverpomogator.domain.model.webinar.FavoriteWebinarModel;

import java.util.Date;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "date_birth")
    private Date date_birth;

    @Column(name = "position")
    private String position;

    @Column(name = "place_work")
    private String place_work;

    @Column(name = "rank")
    private String rank;

    @Column(name = "phone")
    private String phone;

    @Column(name = "completed_profile")
    private Boolean completed_profile;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private FileModel avatar;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    private NewsModel news;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,  orphanRemoval = true)
    Set<FavoriteModel> favorite;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    Set<FavoriteWebinarModel> favorite_webinar;

    @PrePersist
    public void prePersist() {
        if (completed_profile == null) {
            completed_profile = false;
        }
    }
}
