package hu.budgetflix.api.mapper;

import hu.budgetflix.api.model.dto.MovieDto;
import hu.budgetflix.api.model.entity.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto toDto(Movie movie);
}
