package ru.pomogator.serverpomogator.domain.mapper;

import org.mapstruct.*;
import ru.pomogator.serverpomogator.domain.dto.auth.UserRequest;
import ru.pomogator.serverpomogator.domain.dto.auth.UserResponse;
import ru.pomogator.serverpomogator.domain.dto.reviews.ReviewsModelDto;
import ru.pomogator.serverpomogator.domain.model.reviews.ReviewsModel;
import ru.pomogator.serverpomogator.domain.model.user.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "avatar", ignore = true)
    UserResponse toUserResponse(User user);

    @Mapping(source = "avatar.path" , target = "avatar")
    UserResponse toUserList(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserRequest dto, @MappingTarget User user);
}

