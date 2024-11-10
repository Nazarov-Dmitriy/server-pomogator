package ru.pomogator.serverpomogator.domain.mapper;

import org.mapstruct.*;
import ru.pomogator.serverpomogator.domain.dto.material.MaterialResponse;
import ru.pomogator.serverpomogator.domain.dto.webinar.WebinarRequest;
import ru.pomogator.serverpomogator.domain.dto.webinar.WebinarResponse;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;
import ru.pomogator.serverpomogator.domain.model.webinar.WebinarModel;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WebinarMapper {
    WebinarModel toWebinarModel(WebinarRequest webinarRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    WebinarModel partialUpdate(WebinarRequest webinarRequest, @MappingTarget WebinarModel webinarModel);

    @Mapping(target = "tags", expression = "java(tagsToTags(webinarModel.getTags()))")
    @Mapping(source = "preview_img.path", target = "preview_img")
    WebinarResponse toWebinarResponse(WebinarModel webinarModel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @InheritInverseConfiguration(name = "toWebinarModel")
    @Mapping(source = "preview_img.path", target = "preview_img")
    @Mapping(target = "tags", expression = "java(tagsToTags(webinarModel.getTags()))")
    MaterialResponse toMaterialResponse(WebinarModel webinarModel);

    default List<Long> tagsToTags(List<TagsModel> tags) {
        return tags.stream().map(TagsModel::getId).collect(Collectors.toList());
    }
}
