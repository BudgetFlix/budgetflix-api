package hu.budgetflix.api.service;

import hu.budgetflix.api.model.Status;
import hu.budgetflix.api.model.dto.message.MediaMessage;
import hu.budgetflix.api.model.dto.request.UploadMedia;
import hu.budgetflix.api.model.entity.Movie;
import hu.budgetflix.api.producer.VideoProducer;
import hu.budgetflix.api.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class UploadService {

    private final VideoProducer producer;
    private final MovieRepository movieRepository;

    public UploadService(VideoProducer producer, MovieRepository movieRepository) {
        this.producer = producer;
        this.movieRepository = movieRepository;
    }

    public void uploadMovie(UploadMedia metaData) {
        Movie movie = new Movie();
        movie.setTitle(metaData.title());
        movie.setStatus(Status.PROCESS.toString());

        Movie savedMovie = movieRepository.save(movie);

        MediaMessage message = new MediaMessage(
                metaData.jobID(),
                savedMovie.getId(),
                metaData.videos(),
                metaData.type()
        );

        producer.send(message);
    }
}
