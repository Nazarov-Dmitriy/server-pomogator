package ru.pomogator.serverpomogator.domain.model.news;

import jakarta.persistence.*;
import lombok.*;
import ru.pomogator.serverpomogator.domain.model.user.User;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "favorite_news")
public class FavoriteModel {
    @EmbeddedId
    private FavoriteKey pkFavorite = new FavoriteKey();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("newsId")
    @JoinColumn(name = "news_id", referencedColumnName = "id")
    NewsModel news;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    Boolean favorite;

    public FavoriteModel(FavoriteKey pk) {
        var newsPk = new NewsModel();
        newsPk.setId(pk.getNewsId());
        var userPk = new User();
        userPk.setId(pk.getUserId());
        this.pkFavorite = pk;
        this.news = newsPk;
        this.user = userPk;
        favorite = true;
    }
}


