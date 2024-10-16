package ru.pomogator.serverpomogator.repository.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;

@Repository
public interface TagsRepository extends JpaRepository<TagsModel, Long> {
}