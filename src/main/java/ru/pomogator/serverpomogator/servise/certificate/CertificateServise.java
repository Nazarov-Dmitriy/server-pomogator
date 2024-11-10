package ru.pomogator.serverpomogator.servise.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.pomogator.serverpomogator.domain.dto.certificate.CertificateResponse;
import ru.pomogator.serverpomogator.domain.mapper.CertificateMapper;
import ru.pomogator.serverpomogator.domain.model.user.User;
import ru.pomogator.serverpomogator.repository.certificate.CertificateRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CertificateServise {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    public ResponseEntity<?> getCertificate(User user) {
        var certificate = certificateRepository.findByUser(user);
        var list = new ArrayList<CertificateResponse>();
        if (certificate != null) {
            for (var item : certificate) {
                list.add(certificateMapper.toCertificateResponse(item));
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
