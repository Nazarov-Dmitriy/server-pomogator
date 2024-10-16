package ru.pomogator.serverpomogator.servise.subsribe;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.pomogator.serverpomogator.domain.dto.subsribe.SubcribeDto;
import ru.pomogator.serverpomogator.domain.model.subscribe.Subcribe;
import ru.pomogator.serverpomogator.repository.subscribe.SubcribeRepository;

@Service
@RequiredArgsConstructor
public class SubscribeServise {
    private final SubcribeRepository subcribeRepository;

    public ResponseEntity<?> subscribe(SubcribeDto body) {
        try {
            if(subcribeRepository.existsByEmail(body.getEmail())){
                return new ResponseEntity<>("subscribe",HttpStatus.OK);
            }
            var subscriber = new Subcribe();
            subscriber.setEmail(body.getEmail());
            subcribeRepository.save(subscriber);
            return new ResponseEntity<>("added", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
