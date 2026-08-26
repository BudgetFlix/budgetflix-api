package hu.budgetflix.api.model.dto.response;

public record TmdbSearchDto(
        String mediaType,
        String title,
        String posterPath,
        String overview,
        String releaseDate,
        String backgroundPath
) {
}