package hu.budgetflix.api.controller;

import hu.budgetflix.api.model.dto.response.StreamDto;
import hu.budgetflix.api.service.StreamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stream")
public class StreamController {

    private final StreamService streamService;

    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<StreamDto> stream(@PathVariable Long id) {

        // TODO auth check

        StreamDto streamDto = streamService.getStreamUrl(id);

        return ResponseEntity.ok(streamDto);
    }
}
