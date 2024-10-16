package ru.pomogator.serverpomogator.repository.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.domain.model.subscribe.Subcribe;

@Repository
public interface SubcribeRepository extends JpaRepository<Subcribe, Long> {
    boolean existsByEmail(String email);

    void removeByEmail(String email);
}