package ru.pomogator.serverpomogator.servise;

import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.repository.news.FileRepository;

@Service
public class FileServise {
    private final FileRepository fileRepository;

    public FileServise(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
    public void addFile(MultipartFile file) {
        try {
//            byte[] fileBytes = file.getBytes();
//            long fileLength = file.getSize();
//            String name = file.getName();
//            System.out.println(name);
//            System.out.println(file.getOriginalFilename());
//
//            FileModel build = FileModel.builder().name(file.getOriginalFilename())
//                    .content(fileBytes).size(fileLength).build();
//            fileRepository.save(build);

        } catch (EmptyResultDataAccessException e) {
            throw new BadRequest("Error input data");
        }
    }
}
