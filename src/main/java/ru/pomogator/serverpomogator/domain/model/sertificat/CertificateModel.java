package ru.pomogator.serverpomogator.domain.model.sertificat;


import jakarta.persistence.*;
import lombok.*;
import ru.pomogator.serverpomogator.domain.model.base.BaseEntity;
import ru.pomogator.serverpomogator.domain.model.user.User;

@Data
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "certificate")
public class CertificateModel extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(nullable = false)
    private Long webinarId;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


}
