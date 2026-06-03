package hu.budgetflix.api.controller;

import hu.budgetflix.api.model.dto.response.JobDto;
import hu.budgetflix.api.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/job")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobDto>> getAllJob (){

        List<JobDto> jobs = jobService.getAllJob();

        return ResponseEntity.ok(jobs);
    }
}
