package hu.budgetflix.api.service;

import hu.budgetflix.api.mapper.JobMapper;
import hu.budgetflix.api.model.dto.message.MediaMessage;
import hu.budgetflix.api.model.dto.response.JobDto;
import hu.budgetflix.api.model.entity.Job;
import hu.budgetflix.api.model.entity.Movie;
import hu.budgetflix.api.producer.VideoProducer;
import hu.budgetflix.api.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final VideoProducer videoProducer;

    public JobService(JobRepository jobRepository, JobMapper jobMapper, VideoProducer videoProducer) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.videoProducer = videoProducer;
    }

    public List<JobDto> getAllJob() {
        return jobRepository.getJobsBy()
                .stream()
                .map(jobMapper::toDto)
                .toList();
    }

    public void retry(UUID id) {
        Job job = jobRepository.getJobById(id);
        Movie movie = job.getMovie();
        MediaMessage msg = new MediaMessage(job.getId(), movie.getId(), job.getVideos(),movie.getMediaType(),true);

        videoProducer.send(msg);
    }
}
