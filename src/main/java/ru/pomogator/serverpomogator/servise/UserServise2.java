package ru.pomogator.serverpomogator.servise;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pomogator.serverpomogator.domain.dto.UserDto;
import ru.pomogator.serverpomogator.domain.model.Role;
import ru.pomogator.serverpomogator.domain.model.User;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.repository.UserRepository;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServise2 {
//    @Value("${bcrypt.salt}")
//    private int salt;
//
//    private final UserRepository userRepository;
//
//    public UserServise2(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public ResponseEntity<Void> rigisterUser(UserDto body) {
//        try {
//            var role = "";
//
//            if (StringUtils.isEmpty(body.getRole())) {
//                System.out.println("role isEmpty");
//                role = "user";
//            } else {
//                role = body.getRole();
//            }
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(salt, new SecureRandom(new byte[20]));
//
//            User build = User.builder().login(body.getLogin()).password(encoder.encode(body.getPassword()))
//                    .role(Role.valueOf(role)).name(body.getName()).surname(body.getSurname()).patronymic(body.getPatronymic()).email(body.getEmail()).build();
//
//            System.out.println(build);
//
//            userRepository.save(build);
//
//            return new ResponseEntity<>(HttpStatus.OK);
//
//        } catch (EmptyResultDataAccessException e) {
//            System.out.println(e.getMessage());
//            throw new BadRequest("Error input data");
//        }
//    }
//
//    public ResponseEntity<Map<Object, Object>> login(UserDto body, HttpServletRequest request) {
//        Optional<User> user = userRepository.findByLogin(body.getLogin());
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(salt, new SecureRandom(new byte[20]));
//        System.out.println(encoder.encode(body.getPassword()));
//        if (user.isPresent()) {
//
//            if(encoder.matches(body.getPassword(), user.get().getPassword())) {
//                HttpSession session = request.getSession();
//                session.setAttribute("login", user.get().getLogin());
//                session.setAttribute("user-id", user.get().getId());
//                Map<Object, Object> response = new HashMap<>();
//                response.put("login", user.get().getLogin());
//                response.put("name", user.get().getName());
//                response.put("surname", user.get().getSurname());
//                response.put("patronymic", user.get().getPatronymic());
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            } else {
//                throw new BadRequest("password");
//            }
//        } else {
//            throw new BadRequest("login");
//        }
//    }
}


