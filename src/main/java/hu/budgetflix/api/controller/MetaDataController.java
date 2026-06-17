package hu.budgetflix.api.controller;

import hu.budgetflix.api.model.dto.response.TmdbSearchDto;
import hu.budgetflix.api.service.MetaDataService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/metadata")
@RequiredArgsConstructor
public class MetaDataController {

    private final MetaDataService metaDataService;

    @GetMapping("/client")
    public List<TmdbSearchDto> getmetadata(@RequestParam String query) {
        return metaDataService.searchMulti(query);
    }
}
