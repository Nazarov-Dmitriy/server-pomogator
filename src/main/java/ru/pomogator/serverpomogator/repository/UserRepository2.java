package ru.pomogator.serverpomogator.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.domain.model.User;


@Repository
public interface UserRepository2 extends JpaRepository<User, Long> {
//    Optional<> findByUsername(String username);
//
//    Optional<User> findByLogin(String login);
//
//    boolean existsByUsername(String username);
//
//    boolean existsByEmail(String email);
//
//    boolean existsByLogin(String login);
}