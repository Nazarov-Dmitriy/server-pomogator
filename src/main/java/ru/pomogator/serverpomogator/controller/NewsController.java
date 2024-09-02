package ru.pomogator.serverpomogator.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.pomogator.serverpomogator.dto.news.NewsAddDto;
import ru.pomogator.serverpomogator.dto.news.NewsDto;
import ru.pomogator.serverpomogator.servise.NewsServise;

@RestController
@RequestMapping("news")
public class NewsController {
    private final NewsServise newsServise;

    public NewsController(NewsServise newsServise) {
        this.newsServise = newsServise;
    }

    @PostMapping(path ="/add" , consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Void> addNews(@Validated(NewsAddDto.addNews.class) @ModelAttribute NewsAddDto body ) {
        return newsServise.addNews(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> news(@PathVariable  Long id) {
        return newsServise.get(id);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(required = false) Long category, @RequestParam(required = false) String tags) {
             return newsServise.list(category ,tags);
    }

    @GetMapping("/tags")
    public ResponseEntity<?> getTags() {
        return newsServise.getTags();
    }

    @GetMapping("/categoty")
    public ResponseEntity<?> getCategory() {
        return newsServise.getCategory();
    }
}
