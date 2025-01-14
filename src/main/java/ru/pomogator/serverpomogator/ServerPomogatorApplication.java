package ru.pomogator.serverpomogator;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.pomogator.serverpomogator.utils.FolderCreate;

import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class ServerPomogatorApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+3:00"));
        SpringApplication application = new SpringApplication(ServerPomogatorApplication.class);
        application.setAdditionalProfiles("ssl");
        application.run(args);
        FolderCreate.intialFolder();
    }
}
