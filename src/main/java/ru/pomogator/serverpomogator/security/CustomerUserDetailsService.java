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

    public UserDetailsService userDetailsService() {
           return this;
    }

    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        System.out.println(user);
        if (user.isEmpty()) {
            throw new BadRequest("Bad credentials");
        }

        return new JwtUser(user.get().getId(), user.get().getUsername(), user.get().getPassword(), user.get().getEmail(), user.get().getRole());
    }
}
