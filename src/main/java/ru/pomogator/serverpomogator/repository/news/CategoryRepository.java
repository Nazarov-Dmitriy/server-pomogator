package ru.pomogator.serverpomogator.repository.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pomogator.serverpomogator.model.news.CategoryModel;


@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

}