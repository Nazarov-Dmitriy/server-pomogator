package ru.pomogator.serverpomogator.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.domain.dto.news.NewsRequest;
import ru.pomogator.serverpomogator.domain.dto.webinar.WebinarRequest;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;
import ru.pomogator.serverpomogator.domain.model.user.User;
import ru.pomogator.serverpomogator.servise.NewsServise;
import ru.pomogator.serverpomogator.servise.webinar.WebinarServise;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("webinar")
public class WebinarController {
    private final WebinarServise webinarServise;

    @PostMapping(path = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addWebinar(@Validated WebinarRequest req, @ModelAttribute MultipartFile preview_img) {
        return webinarServise.addWebinar(req, preview_img);
    }

    @PutMapping(path = "/edit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> editWebinar(@Validated WebinarRequest req, @ModelAttribute MultipartFile preview_img) {
        return webinarServise.editWebinar(req, preview_img);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(required = false) List<String> tags) {
        return webinarServise.list(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> webinar(@PathVariable Long id) {
        return webinarServise.get(id);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> setShow(@PathVariable Long id) {
        return webinarServise.setShow(id);
    }

    @GetMapping("/like")
    public ResponseEntity<?> setLike(@RequestParam Long id, @RequestParam(required = false) Integer like, @RequestParam(required = false) Integer dislike) {
        return webinarServise.setLike(id, like, dislike);
    }

    @GetMapping("/add-favorite")
    public ResponseEntity<?> addWebinarFavorite(@RequestParam Long webinar_id, @RequestParam Long user_id) {
        return webinarServise.addWebinarFavorite(webinar_id, user_id);
    }

    @GetMapping("/favorite")
    public ResponseEntity<?> getWebinarFavorite(@RequestParam Long webinar_id, @RequestParam Long user_id) {
        return webinarServise.getWebinarFavorite(webinar_id, user_id);
    }

    @GetMapping("/remove-favorite")
    public ResponseEntity<?> removeWebinarFavorite(@RequestParam Long webinar_id, @RequestParam Long user_id) {
        return webinarServise.removeWebinarFavorite(webinar_id, user_id);
    }

    @GetMapping("/favorite/user/{id}")
    public ResponseEntity<?> getFavoriteWebinarUser(@PathVariable Long id, @RequestParam(required = false) List<TagsModel> tags) {
        return webinarServise.getFavoriteWebinarUser(id, tags);
    }

    @GetMapping("/remove/{id}")
    public ResponseEntity<?> removeWebinar(@PathVariable Long id) {
        return webinarServise.remove(id);
    }

    @GetMapping("/subscribe")
    public ResponseEntity<?> subscribeWebinar(@RequestParam Long webinar_id, @RequestParam User user) {
        return webinarServise.subscribeWebinar(webinar_id, user);
    }

    @GetMapping("/get-subscribe")
    public ResponseEntity<?> getSubscribeUser(@RequestParam Long webinar_id, @RequestParam List<User> user) {
        return webinarServise.getSubscribeUser(webinar_id, user);
    }

    @GetMapping("/set-status")
    public ResponseEntity<?> setStatus(@RequestParam Long webinar_id) {
                return webinarServise.setStatus(webinar_id);
    }
}
