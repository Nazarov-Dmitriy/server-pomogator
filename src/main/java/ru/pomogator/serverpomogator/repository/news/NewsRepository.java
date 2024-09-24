package ru.pomogator.serverpomogator.repository.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.domain.model.news.NewsModel;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;

import java.util.List;


@Repository
public interface NewsRepository extends JpaRepository<NewsModel, Long> {
    List<NewsModel> findByCategoryId(Long category);

    List<NewsModel> findByTagsIn(List<String> tags);
}