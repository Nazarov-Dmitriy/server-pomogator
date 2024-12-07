package ru.pomogator.serverpomogator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;
import ru.pomogator.serverpomogator.domain.model.user.User;
import ru.pomogator.serverpomogator.servise.certificate.CertificateServise;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateServise certificateServise;

    @GetMapping("/certificate/my/{user}")
    public ResponseEntity<?> getAllCertificate(@PathVariable User user) {
        return certificateServise.getAllCertificate(user);
    }

    @GetMapping("/certificate/webinar")
    public ResponseEntity<?> getUserWebinarCertificate(@RequestParam Long user_id , @RequestParam Long webinar_id) {
        return certificateServise.getUserWebinarCertificate(user_id, webinar_id);
    }

    @GetMapping("/certificate/add")
    public ResponseEntity<?> setUserWebinarCertificate(@RequestParam Long user_id , @RequestParam Long webinar_id) {
        return certificateServise.setUserWebinarCertificate(user_id, webinar_id);
    }
}
