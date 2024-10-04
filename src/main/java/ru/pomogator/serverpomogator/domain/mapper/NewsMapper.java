package ru.pomogator.serverpomogator.domain.mapper;

import org.mapstruct.*;
import ru.pomogator.serverpomogator.domain.dto.news.NewsRequest;
import ru.pomogator.serverpomogator.domain.dto.news.NewsResponse;
import ru.pomogator.serverpomogator.domain.model.news.NewsModel;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewsMapper {
    NewsModel toNewsModel(NewsRequest newsRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NewsModel partialUpdate(NewsRequest newsRequest, @MappingTarget NewsModel newsModel);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @InheritInverseConfiguration(name = "toNewsModel")
    @Mapping(source = "file.path", target = "file")
    @Mapping(source = "category.id", target = "category")
    @Mapping(target = "tags", expression = "java(tagsToTags(newsModel.getTags()))")
    @Mapping(source = "author.avatar.path", target = "author.avatarPath")
    NewsResponse toNewsResponse(NewsModel newsModel);

    default List<Long> tagsToTags(List<TagsModel> tags) {
        return tags.stream().map(TagsModel::getId).collect(Collectors.toList());
    }

    @Condition
    default boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}