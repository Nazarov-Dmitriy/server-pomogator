package ru.pomogator.serverpomogator.servise.certificate;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.pomogator.serverpomogator.domain.dto.certificate.CertificateResponse;
import ru.pomogator.serverpomogator.domain.mapper.CertificateMapper;
import ru.pomogator.serverpomogator.domain.model.sertificat.CertificateModel;
import ru.pomogator.serverpomogator.domain.model.user.User;
import ru.pomogator.serverpomogator.exception.InternalServerError;
import ru.pomogator.serverpomogator.repository.certificate.CertificateRepository;
import ru.pomogator.serverpomogator.repository.user.UserRepository;
import ru.pomogator.serverpomogator.repository.webinar.WebinarRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CertificateServise {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final WebinarRepository webinarRepository;
    private final UserRepository userRepository;


    public ResponseEntity<?> getAllCertificate(User user) {
        var certificate = certificateRepository.findByUser(user);
        var list = new ArrayList<CertificateResponse>();
        if (certificate != null) {
            for (var item : certificate) {
                list.add(certificateMapper.toCertificateResponse(item));
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public ResponseEntity<?> getUserWebinarCertificate(Long userId, Long webinarId) {
        var certificate = certificateRepository.findByWebinarIdAndUser_Id(webinarId, userId);
        if (certificate != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> setUserWebinarCertificate(Long userId, Long webinarId) {
        try {
            var currentCertificate = certificateRepository.findByWebinarIdAndUser_Id(webinarId, userId);
            if (currentCertificate != null) {
                return new ResponseEntity<>(HttpStatus.OK);
            }

            var user = userRepository.findById(userId);
            var webinar = webinarRepository.findById(webinarId);
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            if (user.isPresent() && webinar.isPresent()) {
                var certificate = new CertificateModel();
                certificate.setTitle(webinar.get().getTitle());
                certificate.setDate(df.format(new Date()));
                certificate.setUser(user.get());
                certificate.setWebinarId(webinarId);
                certificateRepository.save(certificate);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }
    }
}
