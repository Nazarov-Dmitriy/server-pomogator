package ru.pomogator.serverpomogator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.domain.dto.reviews.ReviewsDto;
import ru.pomogator.serverpomogator.servise.reviews.ReviewsServise;

@RestController
@RequestMapping("reviews/")
@RequiredArgsConstructor
public class ReviewsController {
    private final ReviewsServise reviewsServise;

    @PostMapping(path = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addReviews(@Validated ReviewsDto req, @ModelAttribute MultipartFile file) {
        return reviewsServise.addReviews(req, file);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<?> getList() {
        return reviewsServise.getList();
    }

    @PutMapping(path = "/edit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> editReview(@Validated ReviewsDto req, @ModelAttribute MultipartFile file) {
        return reviewsServise.editReview(req, file);
    }

    @GetMapping(path = "/remove/{id}")
    public ResponseEntity<?> removeReviews(@PathVariable Long id) {
        return reviewsServise.removeReviews(id);
    }
}
