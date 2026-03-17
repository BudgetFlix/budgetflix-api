package hu.budgetflix.api.repository;

import hu.budgetflix.api.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
