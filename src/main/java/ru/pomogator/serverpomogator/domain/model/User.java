package ru.pomogator.serverpomogator.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

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


    @OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FileModel avatar;

    @PrePersist
    public void prePersist() {
        if (completed_profile == null) {
            completed_profile = false;
        }
    }
}
