package ru.pomogator.serverpomogator.repository.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.domain.model.news.NewsModel;

import java.util.List;


@Repository
public interface NewsRepository extends JpaRepository<NewsModel, Long> {
    List<NewsModel> findByCategoryIdAndPublished(Long category, boolean published);

    List<NewsModel> findByTagsInAndPublished(List<String> tags, boolean published);

    List<NewsModel> findByTagsIn(List<String> tags);

    List<NewsModel> findByAuthorId(Long id);

    List<NewsModel> findByAuthorIdAndPublished(Long id, Boolean published);

    List<NewsModel> findByPublished(boolean published);

    List<NewsModel> findTop3ByPublishedOrderByCreatedAtDesc(boolean published);

    List<NewsModel> findByAuthorIdAndTagsIn(Long id, List<String> tags);

    List<NewsModel> findByAuthorIdAndTagsInAndPublished(Long id, List<String> tags, Boolean published);

    void deleteById(Long id);
}


