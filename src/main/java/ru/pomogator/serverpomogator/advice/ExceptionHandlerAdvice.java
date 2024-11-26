package ru.pomogator.serverpomogator.advice;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.exception.InternalServerError;
import ru.pomogator.serverpomogator.exception.Unauthorized;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
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
         UUID id = UUID.randomUUID();
        if(!exception.getErrors().isEmpty()) {
            return new ResponseEntity<>(exception.getErrors(), HttpStatus.BAD_REQUEST);
        }

        if (exception.getMessage().equals("Пользователь не найден")) {
            Map<String, String> errors = new HashMap<>();
            errors.put("email", "Пользователь не найден");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        if (exception.getMessage().equals("Bad password")) {
            Map<String, String> errors = new HashMap<>();
            errors.put("password", "Не правильный пароль");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        ErrorMessage errorMessage = new ErrorMessage(id, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(Unauthorized.class)
    public ResponseEntity<ErrorMessage> unauthorized(Unauthorized exception) {

        UUID id = UUID.randomUUID();
        ErrorMessage errorMessage = new ErrorMessage(id, exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ErrorMessage> internalServerError(InternalServerError exception, HttpServletRequest req) {
        UUID id = UUID.randomUUID();
        ErrorMessage errorMessage = new ErrorMessage(id, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> IllegalArgumentException(IllegalArgumentException exception, HttpServletRequest req) {
        UUID id = UUID.randomUUID();
        ErrorMessage errorMessage = new ErrorMessage(id, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> AuthenticationException(AuthenticationException exception, HttpServletRequest req) {
        UUID id = UUID.randomUUID();
        ErrorMessage errorMessage = new ErrorMessage(id, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> UsernameNotFoundException(UsernameNotFoundException exception, HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();
        errors.put("email", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> ExpiredJwtException(ExpiredJwtException exception, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
