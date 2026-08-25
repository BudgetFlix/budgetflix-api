package hu.budgetflix.api.mapper;

import hu.budgetflix.api.model.dto.response.MovieDto;
import hu.budgetflix.api.model.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public MovieDto toDto(Movie movie){
        return new MovieDto(movie.getTitle(), movie.getId(),giveImagePath(movie.getId()));
    }

    private String giveImagePath (Long id){return "/images/movies/" + id + "/poster.jpg";}
}
