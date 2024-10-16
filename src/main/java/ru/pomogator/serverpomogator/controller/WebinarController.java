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
import ru.pomogator.serverpomogator.servise.NewsServise;
import ru.pomogator.serverpomogator.servise.webinar.WebinarServise;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("webinar")
public class WebinarController {
    private final WebinarServise webinarServise;

    @PostMapping(path = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addNews(@Validated WebinarRequest req, @ModelAttribute MultipartFile preview_img) {
        return webinarServise.addWebinar(req, preview_img);
    }

    //
//    @PutMapping(path = "/edit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<?> editNews(@Validated NewsRequest req, @ModelAttribute MultipartFile file) {
//        return newsServise.editNews(req, file);
//    }
//
    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(required = false) List<String> tags) {
        return webinarServise.list(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> news(@PathVariable Long id) {
        return webinarServise.get(id);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> setShow(@PathVariable Long id) {
        System.out.println(111);
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


//

//

//
//    @GetMapping("/my-material")
//    public ResponseEntity<?> userNews(@RequestParam Long id, @RequestParam(required = false) List<String> tags) {
//        return newsServise.userNews(id, tags);
//    }
//
//    @GetMapping("/remove/{id}")
//    public ResponseEntity<?> removeNews(@PathVariable Long id) {
//        return newsServise.remove(id);
//    }
//

//

//

}
