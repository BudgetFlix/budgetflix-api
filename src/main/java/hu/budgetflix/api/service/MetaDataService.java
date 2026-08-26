package hu.budgetflix.api.service;

import hu.budgetflix.api.client.TmdbClient;
import hu.budgetflix.api.model.dto.response.TmdbSearchDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaDataService {

    private static final String TMDB_IMAGE_BASE_URL =
            "https://image.tmdb.org/t/p/";

    private static final String POSTER_SIZE = "w500";
    private static final String BACKDROP_SIZE = "w1280";

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

                        item.posterPath() != null
                                ? TMDB_IMAGE_BASE_URL + POSTER_SIZE + item.posterPath()
                                : null,

                        item.overview(),

                        item.releaseDate() != null
                                ? item.releaseDate()
                                : item.firstAirDate(),

                        item.backgroundPath() != null
                                ? TMDB_IMAGE_BASE_URL + BACKDROP_SIZE + item.backgroundPath()
                                : null
                ))
                .toList();
    }
}
