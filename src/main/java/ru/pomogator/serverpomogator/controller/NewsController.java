package ru.pomogator.serverpomogator.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.domain.dto.news.NewsRequest;
import ru.pomogator.serverpomogator.servise.NewsServise;

import java.util.List;

@RestController
@RequestMapping("news")
public class NewsController {
    private final NewsServise newsServise;

    public NewsController(NewsServise newsServise) {
        this.newsServise = newsServise;
    }

    @PostMapping(path = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> addNews(@Validated NewsRequest req, @ModelAttribute MultipartFile file) {
        return newsServise.addNews(req, file);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(required = false) Long category, @RequestParam(required = false) List<String> tags) {
        return newsServise.list(category, tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> news(@PathVariable Long id) {
        return newsServise.get(id);
    }

    @GetMapping("/tags")
    public ResponseEntity<?> getTags() {
        return newsServise.getTags();
    }

    @GetMapping("/category")
    public ResponseEntity<?> getCategory() {
        return newsServise.getCategory();
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> setShow(@PathVariable Long id) {
        return newsServise.setShow(id);
    }

    @GetMapping("/like")
    public ResponseEntity<?> setLike(@RequestParam Long id, @RequestParam(required = false) Integer like, @RequestParam(required = false) Integer dislike) {
        return newsServise.setLike(id, like, dislike);
    }
}
