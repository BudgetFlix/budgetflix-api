package hu.budgetflix.api.mapper;

import hu.budgetflix.api.model.dto.response.MovieDto;
import hu.budgetflix.api.model.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public MovieDto toDto(Movie movie){
        long id = movie.getId();
        return new MovieDto(movie.getTitle(), id, movie.getOverview(), givePosterPath(id),giveBackgroundPath(id));
    }

    private String givePosterPath (long id){return "/images/movies/" + id + "/poster.jpg";}
    private String giveBackgroundPath (long id){return "/images/movies/" + id + "/background.jpg";}
}
