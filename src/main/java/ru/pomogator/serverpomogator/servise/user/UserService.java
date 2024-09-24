package ru.pomogator.serverpomogator.servise.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pomogator.serverpomogator.domain.dto.auth.AuthenticationResponse;
import ru.pomogator.serverpomogator.domain.dto.auth.UserRequest;
import ru.pomogator.serverpomogator.domain.mapper.UserMapper;
import ru.pomogator.serverpomogator.domain.model.User;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.repository.FileRepository;
import ru.pomogator.serverpomogator.repository.UserRepository;
import ru.pomogator.serverpomogator.security.JwtUser;
import ru.pomogator.serverpomogator.utils.FileCreate;
import ru.pomogator.serverpomogator.utils.HeaderToken;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final FileRepository fileRepository;

    public User save(User user) {
        return repository.save(user);
    }

    public User create(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new BadRequest("Пользователь с таким email уже существует");
        }
        return save(user);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public AuthenticationResponse updateUserInfo(UserRequest request) {
        String jwt = "";
        if (!request.getEmail().equals(request.getCurrent_email())) {
            if (repository.existsByEmail(request.getEmail())) {
                throw new BadRequest("Пользователь с таким email уже существует");
            }
        }
        Optional<User> user = repository.findByEmail(request.getCurrent_email());
        if (user.isPresent()) {
            userMapper.updateUserFromDto(request, user.get());
            repository.save(user.get());
            var responseUser = userMapper.toUserResponse(user.get());

            if (user.get().getAvatar() != null) {
                responseUser.setAvatar(user.get().getAvatar().getPath());
            }
            if (!user.get().getEmail().equals(request.getCurrent_email())) {
                var jwtUser = new JwtUser(user.get());
                jwt = jwtService.generateToken(jwtUser);
                return new AuthenticationResponse(jwt, responseUser);
            } else {
                return new AuthenticationResponse(jwt, responseUser);
            }
        } else {
            throw new BadRequest("Bad email");
        }
    }

    public ResponseEntity<Void> changePassword(UserRequest req, HttpServletRequest request) {
        var token = HeaderToken.getToken(request);
        var email = jwtService.extractUserName(token);
        Optional<User> user = repository.findByEmail(email);

        if (user.isPresent()) {
            if (!BCrypt.checkpw(req.getPassword(), user.get().getPassword())) {
                throw new BadRequest("Bad password");
            } else {
                user.get().setPassword(passwordEncoder.encode(req.getNew_password()));
                repository.save(user.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            throw new BadRequest("Bad email");
        }
    }

    public ResponseEntity<?> addAvatar(UserRequest req) {
        Optional<User> user = repository.findByEmail(req.getEmail());
        if (user.isPresent()) {
            var path = new StringBuilder();
            path.append("files/user/").append(user.get().getId()).append("/");
            var file = FileCreate.addFile(req.getAvatar(), path);
            System.out.println(file);
            user.get().setAvatar(file);
            repository.save(user.get());
            System.out.println(file.getPath());
            return new ResponseEntity<>(file.getPath(), HttpStatus.OK);
        }
        return null;
    }

    public ResponseEntity<?> removeAvatar(HttpServletRequest request) {
        var token = HeaderToken.getToken(request);
        var email = jwtService.extractUserName(token);
        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            var file = user.get().getAvatar();
            fileRepository.delete(file);
            user.get().setAvatar(null);
            repository.save(user.get());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
