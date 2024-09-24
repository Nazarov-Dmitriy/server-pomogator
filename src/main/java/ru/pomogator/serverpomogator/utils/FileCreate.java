package ru.pomogator.serverpomogator.utils;

import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.domain.model.FileModel;
import ru.pomogator.serverpomogator.exception.InternalServerError;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class FileCreate {

    public static FileModel addFile(MultipartFile file, StringBuilder path) {
        if (!file.isEmpty()) {
            try {
                long fileLength = file.getSize();
                String name = file.getOriginalFilename();

                FolderCreate.createFolder(String.valueOf(path));
                createFile(file, String.valueOf(path), name);

                var fullpath = path.append(name);

                return FileModel.builder().name(name)
                        .path(String.valueOf(fullpath)).size(fileLength).build();
            } catch (Exception e) {
                throw new InternalServerError(" Error getting file list");
            }
        }
        return null;
    }


    public static void createFile(MultipartFile file, String path, String name) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(path + name)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                throw new InternalServerError(" Error getting file list");
            }
        }
    }
}
