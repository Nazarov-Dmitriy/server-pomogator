package ru.pomogator.serverpomogator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pomogator.serverpomogator.domain.dto.subsribe.SubcribeDto;
import ru.pomogator.serverpomogator.servise.subsribe.SubscribeServise;

@RestController
@RequestMapping("send-mail")
@RequiredArgsConstructor
public class EmailController {
    private final SubscribeServise subscribeServise;

    @PostMapping("/faq")
    public ResponseEntity<?> userSubscribe(@RequestBody @Validated SubcribeDto req) {
        return subscribeServise.subscribe(req);
    }
}
