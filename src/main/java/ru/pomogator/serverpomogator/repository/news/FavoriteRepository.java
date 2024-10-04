package ru.pomogator.serverpomogator.repository.news;

import jakarta.persistence.ElementCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.domain.model.news.FavoriteKey;
import ru.pomogator.serverpomogator.domain.model.news.FavoriteModel;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;

import java.util.Collection;
import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteModel, FavoriteKey> {
    void deleteByPkFavorite(FavoriteKey key);

    FavoriteModel findByPkFavorite(FavoriteKey key);

    List<FavoriteModel> findByPkFavorite_UserId(long userId);


    List<FavoriteModel> findByPkFavorite_UserIdAndNews_TagsIn(Long pkFavorite_userId, List<TagsModel> news_tags);
}