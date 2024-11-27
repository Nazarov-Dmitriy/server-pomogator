package ru.pomogator.serverpomogator.domain.model.webinar;

import jakarta.persistence.*;
import lombok.*;
import ru.pomogator.serverpomogator.domain.model.user.User;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "subscribers_webinar")
public class SubscribeWebinarModel {
    @EmbeddedId
    private SubscribeWebinarKey pkSubscribe = new SubscribeWebinarKey();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("webinarId")
    @JoinColumn(name = "webinar_id", referencedColumnName = "id")
    WebinarModel webinar;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;


    public SubscribeWebinarModel(SubscribeWebinarKey pk) {
        var webinarPk = new WebinarModel();
        webinarPk.setId(pk.getWebinarId());
        var userPk = new User();
        userPk.setId(pk.getUserId());
        this.pkSubscribe = pk;
        this.webinar = webinarPk;
        this.user = userPk;
    }
}


