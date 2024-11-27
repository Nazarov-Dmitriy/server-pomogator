package ru.pomogator.serverpomogator.servise.mail;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.domain.dto.email.FormRequest;
import ru.pomogator.serverpomogator.domain.model.news.NewsModel;
import ru.pomogator.serverpomogator.domain.model.subscribe.Subcribe;
import ru.pomogator.serverpomogator.domain.model.user.User;
import ru.pomogator.serverpomogator.domain.model.webinar.SubscribeWebinarModel;
import ru.pomogator.serverpomogator.domain.model.webinar.WebinarModel;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.exception.InternalServerError;
import ru.pomogator.serverpomogator.repository.subscribe.SubcribeRepository;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final SubcribeRepository subcribeRepository;

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private Configuration configuration;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${url-backend}")
    private String urlBackend;

    @Value("${url-frontend}")
    private String urlFrontend;

    public void sendSimpleEmail(Map<String, String> params, MultipartFile file) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(params.get("subject"));
            helper.setFrom(username);
            helper.setTo(params.get("email"));
            String emailContent = sendEmailWithContent(params);
            helper.setText(emailContent, true);
            if (file != null) {
                helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
            }

            emailSender.send(mimeMessage);
        } catch (MailSendException e) {
            subcribeRepository.removeByEmail(params.get("email"));
        } catch (IOException | TemplateException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendEmailWithContent(Map<String, String> params) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        configuration.getTemplate(params.get("template")).process(params, stringWriter);
        return stringWriter.getBuffer().toString();
    }

    public void sendMessageRegisterUser(User user, String password) {
        try {
            var url = urlBackend + "images/logo.png";
            Map<String, String> params = new HashMap<>();
            params.put("email", user.getEmail());
            params.put("subject", "Регистрация ИТ-Помогатор");
            params.put("password", password);
            params.put("link_img", url);
            params.put("template", "register.ftlh");
            params.put("link_sait", urlFrontend.split(",")[0]);
            sendSimpleEmail(params, null);
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }
    }

    @Async
    public void sendMessageMaterial(List<Subcribe> subsribeUsers, String path, NewsModel news) {
        for (var item : subsribeUsers) {
            try {
                var url = urlFrontend.split(",")[0] + path;
                Map<String, String> params = new HashMap<>();
                params.put("email", item.getEmail());
                params.put("subject", news.getTitle());
                params.put("title", news.getTitle());
                params.put("description", news.getAnnotation());
                params.put("template", "material.ftlh");
                params.put("link_sait", url);
                sendSimpleEmail(params, null);
            } catch (Exception e) {
                throw new InternalServerError(e.getMessage());
            }
        }
    }

    @Async
    public void sendMessageWebinar(List<Subcribe> subsribeUsers, String pathMaterial, WebinarModel webinar, String date_translation) {
        for (var item : subsribeUsers) {
            try {
                var url = urlFrontend.split(",")[0] + pathMaterial;
                Map<String, String> params = new HashMap<>();
                params.put("email", item.getEmail());
                params.put("subject", "Вебинар: " + webinar.getTitle());
                params.put("title", "Вебинар: " + webinar.getTitle());
                params.put("description", webinar.getAnnotation());
                params.put("template", "material-webinar.ftlh");
                params.put("link_sait", url);
                params.put("date_translation", date_translation);
                sendSimpleEmail(params, null);
            } catch (Exception e) {
                throw new InternalServerError(e.getMessage());
            }
        }
    }

    @Async
    public void sendRemindersWebinar(List<SubscribeWebinarModel> subscriberUsers, String pathMaterial, WebinarModel webinar, String date_translation) {
        for (var item : subscriberUsers) {
            try {
                var url = urlFrontend.split(",")[0] + pathMaterial;
                Map<String, String> params = new HashMap<>();
                params.put("email", item.getUser().getEmail());
                params.put("subject", "Напоминание сегодня проходит вебинар");
                params.put("theme", webinar.getTitle());
                params.put("template", "reminder-webinar.ftlh");
                params.put("link_sait", url);
                params.put("date", date_translation);
                sendSimpleEmail(params, null);
            } catch (Exception e) {
                throw new InternalServerError(e.getMessage());
            }
        }
    }

    public ResponseEntity<?> sendFaq(FormRequest req) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("email", username);
            params.put("subject", "Вопросы и предложения");
            params.put("name", req.getName());
            params.put("phone", req.getPhone());
            params.put("email_user", req.getEmail());
            params.put("question", req.getQuestion());
            params.put("template", "faq.ftlh");
            sendSimpleEmail(params, null);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("error", "ошибка данных");
            throw new BadRequest("error", errors);
        }
    }

    public ResponseEntity<?> sendOfferMaterial(FormRequest req, MultipartFile file) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("email", username);
            params.put("subject", "Предложить тему материала");
            params.put("name", req.getName());
            params.put("phone", req.getPhone());
            params.put("email_user", req.getEmail());
            params.put("question", req.getQuestion());
            params.put("template", "faq.ftlh");
            sendSimpleEmail(params, file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("error", "ошибка данных");
            throw new BadRequest("error", errors);
        }
    }

    public void sendForGotPassword(String password, String email) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("subject", "Восстановление пароля");
            params.put("password", password);
            params.put("template", "for-got-password.ftlh");
            sendSimpleEmail(params, null);
            ResponseEntity.ok().build();
        } catch (Exception e) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("error", "ошибка данных");
            throw new BadRequest("error", errors);
        }
    }
}