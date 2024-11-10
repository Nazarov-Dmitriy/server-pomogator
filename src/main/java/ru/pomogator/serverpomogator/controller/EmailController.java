package ru.pomogator.serverpomogator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.domain.dto.email.FormRequest;
import ru.pomogator.serverpomogator.servise.mail.EmailService;

@RestController
@RequestMapping("send-mail")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    @PostMapping("/faq")
    public ResponseEntity<?> sendFaq(@RequestBody @Validated FormRequest req) {
        return emailService.sendFaq(req);
    }

    @PostMapping(path ="/material",  consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> sendMaterial(@Validated FormRequest req, @ModelAttribute MultipartFile file) {
        return emailService.sendOfferMaterial(req, file);
    }
}
