package ru.pomogator.serverpomogator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pomogator.serverpomogator.domain.model.user.User;
import ru.pomogator.serverpomogator.servise.certificate.CertificateServise;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateServise certificateServise;

    @GetMapping("/certificate/my/{user}")
    public ResponseEntity<?> getCertificate(@PathVariable User user) {
        return certificateServise.getCertificate(user);
    }
}
