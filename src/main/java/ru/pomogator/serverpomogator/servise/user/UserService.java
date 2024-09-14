package ru.pomogator.serverpomogator.servise.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pomogator.serverpomogator.domain.dto.auth.AuthenticationRequest;
import ru.pomogator.serverpomogator.domain.model.User;
import ru.pomogator.serverpomogator.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public User create(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }
        return save(user);
    }

//    @Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
//            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    public interface PlaceMapper {
//        void updatePlacePartial(@MappingTarget Place placeEntity,
//                                UpdatePlaceRequest updatePlaceRequest);

    @Transactional
    public User registerInfo(AuthenticationRequest userInfoRequest) {
//        var user = repository.findByEmail(userInfoRequest.getEmail())
//                .map(existed -> updateEntity((HttpResponse) existed, (HttpEntity) userInfoRequest));
//       var user = repository.findByEmail(userInfoRequest.getEmail());
//        System.out.println(user);
        return new User();
    }
}
