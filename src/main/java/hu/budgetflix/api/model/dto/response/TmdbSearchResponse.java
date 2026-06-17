package hu.budgetflix.api.model.dto.response;

import java.util.List;

public record TmdbSearchResponse(
        List<TmdbSearchRawDto> results
) {
}
