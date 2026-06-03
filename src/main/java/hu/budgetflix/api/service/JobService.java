package hu.budgetflix.api.service;

import hu.budgetflix.api.mapper.JobMapper;
import hu.budgetflix.api.model.dto.response.JobDto;
import hu.budgetflix.api.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    public JobService(JobRepository jobRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }

    public List<JobDto> getAllJob() {
        return jobRepository.getJobsBy()
                .stream()
                .map(jobMapper::toDto)
                .toList();
    }
}
