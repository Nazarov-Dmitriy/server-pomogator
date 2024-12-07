package ru.pomogator.serverpomogator.repository.certificate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.domain.model.sertificat.CertificateModel;
import ru.pomogator.serverpomogator.domain.model.user.User;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateModel, Long> {
    List<CertificateModel> findByUser(User user);

    CertificateModel findByWebinarIdAndUser_Id(Long webinarId, Long user_id);
}