package hu.budgetflix.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stream")
public class StreamController {

    @GetMapping("/{id}")
    public ResponseEntity<Void> stream (@PathVariable Long id){
        return ResponseEntity.ok()
                .header("X-Accel-Redirect","/internal/media" + "/movies" + id + "/hls/index.m3u8")
                .build();
    }
}
