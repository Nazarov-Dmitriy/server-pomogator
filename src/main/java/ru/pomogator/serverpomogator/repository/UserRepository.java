package ru.pomogator.serverpomogator.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.model.UsersModel;

import java.util.Collection;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UsersModel, Long> {
    @Query(value = "select * from users where login = ?1", nativeQuery = true)
    Optional<UsersModel>findByLogin(String login);
}