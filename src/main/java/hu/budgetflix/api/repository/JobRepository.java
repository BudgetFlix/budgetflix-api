package hu.budgetflix.api.repository;

import hu.budgetflix.api.model.dto.response.JobDto;
import hu.budgetflix.api.model.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job,UUID> {
    Job getJobById(UUID id);
    List<Job> getJobsBy();
}
