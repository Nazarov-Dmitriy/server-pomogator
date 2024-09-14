package ru.pomogator.serverpomogator.servise.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pomogator.serverpomogator.domain.model.User;
import ru.pomogator.serverpomogator.repository.UserRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public User create(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }
        return save(user);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

}
