package hu.budgetflix.api.service;

import hu.budgetflix.api.model.Status;
import hu.budgetflix.api.model.dto.response.StreamDto;
import hu.budgetflix.api.model.entity.Movie;
import hu.budgetflix.api.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StreamService {

    private final MovieRepository movieRepository;


    public StreamService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public StreamDto getStreamUrl(long id) {
        Movie movie = movieRepository.findById(id).orElseThrow();

        if (movie.getStatus() == Status.DONE) {
            return new StreamDto("/stream/movies/" + id + "/hls/index.m3u8");
        }

        throw new RuntimeException("this movie is not ready");
    }
}
