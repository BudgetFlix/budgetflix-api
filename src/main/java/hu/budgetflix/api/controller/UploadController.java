package hu.budgetflix.api.controller;

import hu.budgetflix.api.producer.VideoProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
    private final VideoProducer producer;

    public UploadController(VideoProducer producer) {
        this.producer = producer;
    }

    @PutMapping
    public ResponseEntity<String> send (@RequestParam String msg){
        producer.send(msg);
        return  ResponseEntity.ok("Message sent!");
    }

}
