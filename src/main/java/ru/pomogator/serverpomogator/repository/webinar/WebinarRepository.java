package ru.pomogator.serverpomogator.repository.webinar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.domain.model.webinar.Status;
import ru.pomogator.serverpomogator.domain.model.webinar.WebinarModel;

import java.util.List;


@Repository
public interface WebinarRepository extends JpaRepository<WebinarModel, Long> {

    List<WebinarModel> findByAuthorId(Long id);

    List<WebinarModel> findByAuthorIdAndTagsIn(Long id, List<String> tags);

    void deleteById(Long id);

    List<WebinarModel> findByTagsIn(List<String> tags);

    List<WebinarModel> findByStatus(Status status);

}


