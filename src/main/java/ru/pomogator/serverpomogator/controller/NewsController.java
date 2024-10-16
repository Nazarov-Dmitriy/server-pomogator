package ru.pomogator.serverpomogator.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.domain.dto.news.NewsRequest;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;
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
    public ResponseEntity<?> addNews(@Validated NewsRequest req, @ModelAttribute MultipartFile file) {
        return newsServise.addNews(req, file);
    }

    @PutMapping(path = "/edit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> editNews(@Validated NewsRequest req, @ModelAttribute MultipartFile file) {
        return newsServise.editNews(req, file);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(required = false) Long category, @RequestParam(required = false) List<String> tags) {
        return newsServise.list(category, tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> news(@PathVariable Long id, @RequestParam(required = false) Long user_id) {
        return newsServise.get(id, user_id);
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

//    @GetMapping("/my-material")
//    public ResponseEntity<?> userNews(@RequestParam Long id, @RequestParam(required = false) List<String> tags) {
//        return newsServise.userNews(id, tags);
//    }

    @GetMapping("/remove/{id}")
    public ResponseEntity<?> removeNews(@PathVariable Long id) {
        return newsServise.remove(id);
    }

    @GetMapping("/add-favorite")
    public ResponseEntity<?> addNewsFavorite(@RequestParam Long news_id, @RequestParam Long user_id) {
        return newsServise.addNewsFavorite(news_id, user_id);
    }

    @GetMapping("/remove-favorite")
    public ResponseEntity<?> removeNewsFavorite(@RequestParam Long news_id, @RequestParam Long user_id) {
        return newsServise.removeNewsFavorite(news_id, user_id);
    }

    @GetMapping("/favorite")
    public ResponseEntity<?> getNewsFavorite(@RequestParam Long news_id, @RequestParam Long user_id) {
        return newsServise.getNewsFavorite(news_id, user_id);
    }

    @GetMapping("/favorite/user/{id}")
    public ResponseEntity<?> getFavoriteNewsUser(@PathVariable Long id, @RequestParam(required = false) List<TagsModel> tags) {
        return newsServise.getFavoriteNewsUser(id, tags);
    }
}
