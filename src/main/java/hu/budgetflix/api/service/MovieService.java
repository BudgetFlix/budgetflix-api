package hu.budgetflix.api.service;

import hu.budgetflix.api.exception.NotFoundException;
import hu.budgetflix.api.mapper.MovieMapper;
import hu.budgetflix.api.model.dto.MovieDto;
import hu.budgetflix.api.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieMapper movieMapper;

    private  final MovieRepository movieRepository;

    public MovieService(MovieMapper movieMapper, MovieRepository movieRepository) {
        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
    }

    public List<MovieDto> getAllMovie () throws NotFoundException {
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::toDto)
                .toList();
    }


}
