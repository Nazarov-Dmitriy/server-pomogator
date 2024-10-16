package ru.pomogator.serverpomogator.domain.model.webinar;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FavoriteWebinarKey implements Serializable {
    @Column(name = "webinar_id")
    Long newsId;

    @Column(name = "user_id")
    Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteWebinarKey that = (FavoriteWebinarKey) o;
        return Objects.equals(newsId, that.newsId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsId, userId);
    }
}
