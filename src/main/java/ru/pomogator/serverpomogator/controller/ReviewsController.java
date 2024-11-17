package ru.pomogator.serverpomogator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.domain.dto.news.NewsRequest;
import ru.pomogator.serverpomogator.domain.dto.reviews.ReviewsDto;
import ru.pomogator.serverpomogator.domain.model.user.User;
import ru.pomogator.serverpomogator.servise.certificate.CertificateServise;
import ru.pomogator.serverpomogator.servise.reviews.ReviewsServise;

@RestController
@RequestMapping("admin/reviews")
@RequiredArgsConstructor
public class ReviewsController {
    private final ReviewsServise reviewsServise;

    @PostMapping(path ="/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> getCertificate(@Validated ReviewsDto req, @ModelAttribute MultipartFile file) {
        return reviewsServise.addReviews(req, file);
    }
}
