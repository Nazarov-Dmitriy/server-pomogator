package ru.pomogator.serverpomogator.repository.webinar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.domain.model.webinar.FavoriteWebinarKey;
import ru.pomogator.serverpomogator.domain.model.webinar.FavoriteWebinarModel;
import ru.pomogator.serverpomogator.domain.model.webinar.SubscribeWebinarKey;
import ru.pomogator.serverpomogator.domain.model.webinar.SubscribeWebinarModel;

import java.util.List;

@Repository
public interface WebinarSubscribeRepository extends JpaRepository<SubscribeWebinarModel, SubscribeWebinarKey> {
//    void deleteByPkFavorite(SubscribeWebinarKey key);

    SubscribeWebinarModel findByPkSubscribe(SubscribeWebinarKey key);

    List<SubscribeWebinarModel> findByPkSubscribe_UserId(long userId);

    List<SubscribeWebinarModel> findByPkSubscribe_WebinarId(long webinarId);

//    List<FavoriteWebinarModel> findByPkFavorite_UserIdAndWebinar_TagsIn(Long pkFavorite_userId, List<TagsModel> news_tags);
}
