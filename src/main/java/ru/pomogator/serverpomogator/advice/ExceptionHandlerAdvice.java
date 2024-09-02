package ru.pomogator.serverpomogator.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.exception.InternalServerError;
import ru.pomogator.serverpomogator.exception.Unauthorized;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<?> badRequest(BadRequest exception, HttpServletRequest req) {
        String serverScheme = req.getScheme();
        String serverHost = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getServletPath();
        String targetBase = serverScheme + "://" + serverHost + ":" + serverPort + contextPath;

        ErrorMessageLogin error;
        UUID id = UUID.randomUUID();
        if (exception.getMessage().equals("login")) {
            error = new ErrorMessageLogin(id, new String[]{"Неправильный email"}, new String[]{"Неправильный пароль"});
        } else {
            error = new ErrorMessageLogin(id, new String[]{}, new String[]{"Неправильный пароль"});
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Unauthorized.class)
    public ResponseEntity<ErrorMessage> unauthorized(Unauthorized exception, HttpServletRequest req) {
        String serverScheme = req.getScheme();
        String serverHost = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getServletPath();
        String targetBase = serverScheme + "://" + serverHost + ":" + serverPort + contextPath;
        StringBuilder logText = new StringBuilder();
        UUID id = UUID.randomUUID();
        ErrorMessage errorMessage = new ErrorMessage(id, exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ErrorMessage> internalServerError(InternalServerError exception, HttpServletRequest req) {
        String serverScheme = req.getScheme();
        String serverHost = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getServletPath();
        StringBuilder logText = new StringBuilder();
        String targetBase = serverScheme + "://" + serverHost + ":" + serverPort + contextPath;
        UUID id = UUID.randomUUID();
        ErrorMessage errorMessage = new ErrorMessage(id, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
