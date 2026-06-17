package hu.budgetflix.api.model.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbSearchDto(
        Long id,
        String mediaType,
        String title,
        String posterPath
) {
}