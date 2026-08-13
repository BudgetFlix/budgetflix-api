package hu.budgetflix.api.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbSearchDto(
        String mediaType,
        String title,
        String posterPath,
        String overview,
        String releaseDate
) {
}