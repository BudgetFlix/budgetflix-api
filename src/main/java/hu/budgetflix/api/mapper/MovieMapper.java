package hu.budgetflix.api.mapper;

import hu.budgetflix.api.model.dto.MovieDto;
import hu.budgetflix.api.model.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "title", source = "title")
    MovieDto toDto(Movie movie);
}
