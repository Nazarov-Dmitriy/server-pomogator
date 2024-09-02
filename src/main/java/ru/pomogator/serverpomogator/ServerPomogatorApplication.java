package ru.pomogator.serverpomogator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.pomogator.serverpomogator.utils.FolderCreate;

@SpringBootApplication
public class ServerPomogatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerPomogatorApplication.class, args);
        FolderCreate.intialFolder();
    }
}
