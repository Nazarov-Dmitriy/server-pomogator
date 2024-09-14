package ru.pomogator.serverpomogator.domain.mapper;

import org.mapstruct.*;
import ru.pomogator.serverpomogator.domain.dto.auth.AuthenticationRequest;
import ru.pomogator.serverpomogator.domain.dto.auth.UserResponse;
import ru.pomogator.serverpomogator.domain.model.User;

import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
      unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserResponse toUserResponse(User user);
    UserResponse toUserResponseAuthenticationRequest(AuthenticationRequest authenticationRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(AuthenticationRequest dto, @MappingTarget User entity);

}
