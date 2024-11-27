package ru.pomogator.serverpomogator.domain.mapper;

import org.mapstruct.*;
import ru.pomogator.serverpomogator.domain.dto.reviews.ReviewsDto;
import ru.pomogator.serverpomogator.domain.dto.reviews.ReviewsModelDto;
import ru.pomogator.serverpomogator.domain.model.reviews.ReviewsModel;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewsMapper {
    @Mapping(source = "file.path", target = "filePath")
    ReviewsModelDto toDto(ReviewsModel reviewsModel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ReviewsModel partialUpdate(ReviewsDto reviewsDto, @MappingTarget ReviewsModel reviewsModel);
}