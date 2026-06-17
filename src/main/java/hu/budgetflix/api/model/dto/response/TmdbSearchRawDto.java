package hu.budgetflix.api.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbSearchRawDto(
        Long id,

        @JsonProperty("media_type")
        String mediaType,

        String title,

        String name,

        @JsonProperty("poster_path")
        String posterPath
) {
}