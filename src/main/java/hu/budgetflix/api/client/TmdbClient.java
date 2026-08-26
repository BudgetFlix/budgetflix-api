package hu.budgetflix.api.client;


import hu.budgetflix.api.model.dto.request.TmdbSearchRawDto;
import hu.budgetflix.api.model.dto.response.TmdbSearchResponse;
import hu.budgetflix.api.logging.OutboundHttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;


@Component
public class TmdbClient {

    private final RestClient restClient;

    public TmdbClient(
            @Value("${TMDB_ACCESS_TOKEN}") String accessToken,
            OutboundHttpLoggingInterceptor outboundHttpLoggingInterceptor
    ) {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.themoviedb.org/3")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .requestInterceptor(outboundHttpLoggingInterceptor)
                .build();
    }

    public List<TmdbSearchRawDto> search(String query) {

        TmdbSearchResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/multi")
                        .queryParam("query", query)
                        .queryParam("include_adult", false)
                        .queryParam("page", 1)
                        .build())
                .retrieve()
                .body(TmdbSearchResponse.class);

        return response.results();
    }
}
