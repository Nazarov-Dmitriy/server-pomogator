package ru.pomogator.serverpomogator.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import ru.pomogator.serverpomogator.domain.dto.auth.UserResponse;
import ru.pomogator.serverpomogator.domain.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
//@Mapper(componentModel="spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
