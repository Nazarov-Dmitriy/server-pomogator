package ru.pomogator.serverpomogator.repository.reviews;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.domain.model.reviews.ReviewsModel;

@Repository
public interface ReviewsRepository extends JpaRepository<ReviewsModel, Long> {
}