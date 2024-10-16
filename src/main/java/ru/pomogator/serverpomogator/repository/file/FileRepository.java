package ru.pomogator.serverpomogator.repository.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.domain.model.file.FileModel;

@Repository
public interface FileRepository extends JpaRepository<FileModel, Long> {
}