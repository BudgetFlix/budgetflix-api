package hu.budgetflix.api.service;

import hu.budgetflix.api.model.Status;
import hu.budgetflix.api.model.dto.message.MediaMessage;
import hu.budgetflix.api.model.dto.message.MediaRetryMsg;
import hu.budgetflix.api.model.dto.request.UploadMedia;
import hu.budgetflix.api.model.entity.Job;
import hu.budgetflix.api.model.entity.Movie;
import hu.budgetflix.api.producer.VideoProducer;
import hu.budgetflix.api.repository.JobRepository;
import hu.budgetflix.api.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UploadService {

    private final VideoProducer producer;
    private final MovieRepository movieRepository;
    private final JobRepository jobRepository;

    public UploadService(VideoProducer producer, MovieRepository movieRepository, JobRepository jobRepository) {
        this.producer = producer;
        this.movieRepository = movieRepository;
        this.jobRepository = jobRepository;
    }

    public void uploadMovie(UploadMedia metaData) {
        Movie movie = new Movie();
        movie.setTitle(metaData.title());
        movie.setStatus(Status.PROCESS);

        Movie savedMovie = movieRepository.save(movie);

        Job job = new Job(metaData.jobID(),Status.PROCESS,savedMovie,"",metaData.videos());
        jobRepository.save(job);

        MediaMessage message = new MediaMessage(
                metaData.jobID(),
                savedMovie.getId(),
                metaData.videos(),
                metaData.type()
        );

        producer.send(message);
    }

    @Transactional
    public void handleRetryMovieMsg (MediaRetryMsg msg) {
        Job job = jobRepository.getJobById(msg.id());
        if (job == null) {
            return;
        }
        Movie movie =  job.getMovie();

        if(Status.isDone(msg.status())){
            movie.setStatus(Status.DONE);
            movieRepository.save(movie);
            jobRepository.delete(job);
        } else {
            movie.setStatus(Status.ERROR);
            job.setStatus(Status.ERROR);
            job.setErrorMsg(msg.errorMsg());

            movieRepository.save(movie);
            jobRepository.save(job);
        }


    }
}
