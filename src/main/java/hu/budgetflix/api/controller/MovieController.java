package hu.budgetflix.api.controller;


import hu.budgetflix.api.model.dto.MovieDto;
import hu.budgetflix.api.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovie () {
        List<MovieDto> movies = movieService.getAllMovie();
        return ResponseEntity.ok(movies);
    }
}
