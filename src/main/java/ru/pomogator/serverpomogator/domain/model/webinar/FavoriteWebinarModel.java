package ru.pomogator.serverpomogator.domain.model.webinar;

import jakarta.persistence.*;
import lombok.*;
import ru.pomogator.serverpomogator.domain.model.news.FavoriteKey;
import ru.pomogator.serverpomogator.domain.model.news.NewsModel;
import ru.pomogator.serverpomogator.domain.model.user.User;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "favorite_webinar")
public class FavoriteWebinarModel {
    @EmbeddedId
    private FavoriteWebinarKey pkFavorite = new FavoriteWebinarKey();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("webinarId")
    @JoinColumn(name = "webinar_id", referencedColumnName = "id")
    WebinarModel webinar;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    Boolean favorite;

    public FavoriteWebinarModel(FavoriteWebinarKey pk ) {
        var newsPk = new WebinarModel();
        newsPk.setId(pk.getNewsId());
        var userPk = new User();
        userPk.setId(pk.getUserId());
        this.pkFavorite = pk;
        this.webinar = newsPk;
        this.user = userPk;
        favorite=true;
    }
}


