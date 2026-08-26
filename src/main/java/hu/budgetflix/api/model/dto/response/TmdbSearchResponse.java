package hu.budgetflix.api.model.dto.response;

import hu.budgetflix.api.model.dto.request.TmdbSearchRawDto;

import java.util.List;

public record TmdbSearchResponse(
        List<TmdbSearchRawDto> results
) {
}
