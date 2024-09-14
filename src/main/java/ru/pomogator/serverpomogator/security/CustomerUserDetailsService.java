package ru.pomogator.serverpomogator.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.pomogator.serverpomogator.domain.model.User;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.repository.UserRepository;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public JwtUser loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new BadRequest("Пользователь не найден");
        }
        return new JwtUser(user.get());
    }
}
