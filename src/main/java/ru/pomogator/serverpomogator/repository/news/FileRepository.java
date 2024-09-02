package ru.pomogator.serverpomogator.repository.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.model.FileModel;


@Repository
public interface FileRepository extends JpaRepository<FileModel, Long> {
}