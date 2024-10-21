package ru.pomogator.serverpomogator.servise.mail;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.pomogator.serverpomogator.domain.model.user.User;
import ru.pomogator.serverpomogator.exception.InternalServerError;
import ru.pomogator.serverpomogator.repository.subscribe.SubcribeRepository;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final SubcribeRepository subcribeRepository;

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private  Configuration configuration;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${url-backend}")
    private String urlBackend;

    @Value("${url-frontend}")
    private String urlFrontend;

    public void sendSimpleEmail(Map<String, String> params)  {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject(params.get("subject"));
            helper.setFrom(username);
            helper.setTo(params.get("email"));
            String emailContent = sendEmailWithContent(params);
            helper.setText(emailContent, true);
            emailSender.send(mimeMessage);
        } catch (MailSendException e) {
            subcribeRepository.removeByEmail(params.get("email"));
        }catch (IOException | TemplateException | MessagingException e){
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
            sendSimpleEmail(params);
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }
    }

    public void sendMessageMaterial(String email, String title, String description, String path) {
        try {
            var url =urlFrontend.split(",")[0] + path;
            Map<String, String> params = new HashMap<>();
            params.put("email",email);
            params.put("subject", title);
            params.put("title", title);
            params.put("description", description);
            params.put("template", "material.ftlh");
            params.put("link_sait", url);
            System.out.println(params);
            sendSimpleEmail(params);
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }
    }

    public void sendMessageWebinar(String email, String title, String description, String path, String date_translation) {
        try {
            var url =urlFrontend.split(",")[0] + path;
            Map<String, String> params = new HashMap<>();
            params.put("email",email);
            params.put("subject", title);
            params.put("title", title);
            params.put("description", description);
            params.put("template", "material-webinar.ftlh");
            params.put("link_sait", url);
            params.put("date_translation", date_translation);
            System.out.println(params);
            sendSimpleEmail(params);
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }
    }
}