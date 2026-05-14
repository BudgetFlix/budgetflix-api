package hu.budgetflix.api.controller;

import hu.budgetflix.api.model.dto.request.UploadMedia;
import hu.budgetflix.api.producer.VideoProducer;
import hu.budgetflix.api.service.UploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PutMapping
    public ResponseEntity<String> send (@RequestBody UploadMedia uploadMedia){
        uploadService.uploadMovie(uploadMedia);
        return  ResponseEntity.ok("Message sent!");
    }

}
