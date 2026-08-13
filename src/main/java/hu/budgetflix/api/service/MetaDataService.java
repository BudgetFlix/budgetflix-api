package hu.budgetflix.api.service;

import hu.budgetflix.api.client.TmdbClient;
import hu.budgetflix.api.model.dto.response.TmdbSearchDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaDataService {

    private static final String TMDB_IMAGE_BASE_URL =
            "https://image.tmdb.org/t/p/w500";

    private final TmdbClient tmdbClient;

    public MetaDataService(TmdbClient tmdbClient) {
        this.tmdbClient = tmdbClient;
    }

    public List<TmdbSearchDto> searchMulti(String query) {
        return tmdbClient.search(query)
                .stream()
                .filter(item -> ("movie".equals(item.mediaType())
                        || "tv".equals(item.mediaType())) && item.posterPath() != null)
                .map(item -> new TmdbSearchDto(

                        item.mediaType(),
                        item.title() != null
                                ? item.title()
                                : item.name(),
                        TMDB_IMAGE_BASE_URL + item.posterPath(),
                        item.overview(),
                        item.releaseDate() != null ? item.releaseDate() : item.firstAirDate()
                ))
                .toList();
    }
}
