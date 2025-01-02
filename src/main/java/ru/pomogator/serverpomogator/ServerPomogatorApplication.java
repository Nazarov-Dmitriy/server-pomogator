package ru.pomogator.serverpomogator;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.pomogator.serverpomogator.utils.FolderCreate;

import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class ServerPomogatorApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ServerPomogatorApplication.class);
        application.setAdditionalProfiles("ssl");
        application.run(args);
        FolderCreate.intialFolder();

//        SpringApplication.run(ServerPomogatorApplication.class, args);
//        FolderCreate.intialFolder();
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
    }
}
