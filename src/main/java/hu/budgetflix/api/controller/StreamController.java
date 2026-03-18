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
    public ResponseEntity<String> stream(@PathVariable Long id) {

        // TODO auth check

        String url = "/stream/movies/" + id + "/hls/index.m3u8";

        return ResponseEntity.ok(url);
    }
}
