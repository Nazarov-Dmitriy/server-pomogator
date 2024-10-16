package ru.pomogator.serverpomogator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.pomogator.serverpomogator.domain.dto.subsribe.SubcribeDto;
import ru.pomogator.serverpomogator.servise.subsribe.SubscribeServise;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class SubscribeController {
    private final SubscribeServise subscribeServise;

    @PostMapping("/subscribe")
    public ResponseEntity<?> userSubscribe(@RequestBody @Validated SubcribeDto req) {
        return subscribeServise.subscribe(req);
    }
}
