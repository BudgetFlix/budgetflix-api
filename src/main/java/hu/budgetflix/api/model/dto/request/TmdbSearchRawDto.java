package hu.budgetflix.api.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbSearchRawDto(

        @JsonProperty("media_type")
        String mediaType,

        String title,

        String name,

        @JsonProperty("poster_path")
        String posterPath,

        String overview,

        @JsonProperty("first_air_date")
        String firstAirDate,

        @JsonProperty("release_date")
        String releaseDate,

        @JsonProperty("backdrop_path")
        String backgroundPath


) {
}