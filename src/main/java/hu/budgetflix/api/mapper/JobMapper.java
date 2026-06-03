package hu.budgetflix.api.mapper;

import hu.budgetflix.api.model.dto.response.JobDto;
import hu.budgetflix.api.model.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {
    public JobDto toDto (Job job) {
        return new JobDto(job.getId(),job.getStatus());
    }
}
