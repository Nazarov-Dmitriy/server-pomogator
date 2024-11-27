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
public class SubscribeWebinarKey implements Serializable {
    @Column(name = "webinar_id")
    Long webinarId;

    @Column(name = "user_id")
    Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscribeWebinarKey that = (SubscribeWebinarKey) o;
        return Objects.equals(webinarId, that.webinarId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(webinarId, userId);
    }
}
