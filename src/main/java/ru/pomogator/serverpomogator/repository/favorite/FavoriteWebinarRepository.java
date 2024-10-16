package ru.pomogator.serverpomogator.repository.favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.domain.model.news.FavoriteKey;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;
import ru.pomogator.serverpomogator.domain.model.webinar.FavoriteWebinarKey;
import ru.pomogator.serverpomogator.domain.model.webinar.FavoriteWebinarModel;

import java.util.List;

@Repository
public interface FavoriteWebinarRepository extends JpaRepository<FavoriteWebinarModel, FavoriteKey> {
    void deleteByPkFavorite(FavoriteWebinarKey key);

    FavoriteWebinarModel findByPkFavorite(FavoriteWebinarKey key);

    List<FavoriteWebinarModel> findByPkFavorite_UserId(long userId);

    List<FavoriteWebinarModel> findByPkFavorite_UserIdAndWebinar_TagsIn(Long pkFavorite_userId, List<TagsModel> news_tags);
}
