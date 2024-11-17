package ru.pomogator.serverpomogator.servise.reviews;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.domain.dto.reviews.ReviewsDto;
import ru.pomogator.serverpomogator.domain.model.reviews.ReviewsModel;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.repository.reviews.ReviewsRepository;
import ru.pomogator.serverpomogator.utils.FileCreate;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ReviewsServise {
    private final ReviewsRepository reviewsRepository;

    public ResponseEntity<?> addReviews(ReviewsDto req, MultipartFile file) {
        try {
            if (file != null) {
                var reviews = new ReviewsModel();
                reviews.setDate(req.date());
                reviews.setDescription(req.description());
                reviews.setAuthor(req.author());
                reviewsRepository.save(reviews);

                var path = new StringBuilder();
                path.append("files/reviews/").append(reviews.getId()).append("/");
                var new_file = FileCreate.addFile(file, path);
                reviews.setFile(new_file);
                reviewsRepository.save(reviews);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                HashMap<String, String> errors = new HashMap<>();
                errors.put("file", "Поле не может быть пустым");
                throw new BadRequest("error", errors);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

//    public ResponseEntity<?> subscribe(SubcribeDto body) {
//        try {
//            if(subcribeRepository.existsByEmail(body.getEmail())){
//                return new ResponseEntity<>("subscribe",HttpStatus.OK);
//            }
//            var subscriber = new Subcribe();
//            subscriber.setEmail(body.getEmail());
//            subcribeRepository.save(subscriber);
//            return new ResponseEntity<>("added", HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

