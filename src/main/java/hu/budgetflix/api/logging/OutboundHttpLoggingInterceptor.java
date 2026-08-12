package hu.budgetflix.api.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/** Logs calls made by this API to external HTTP services. */
@Component
public class OutboundHttpLoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger TRAFFIC_LOG = LoggerFactory.getLogger("hu.budgetflix.api.traffic");
    private static final Logger FORMAT_LOG = LoggerFactory.getLogger("hu.budgetflix.api.traffic.format");

    @Override
    public ClientHttpResponse intercept(org.springframework.http.HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        TRAFFIC_LOG.info("Outbound HTTP request: {} {}", request.getMethod(), request.getURI());
        if (FORMAT_LOG.isDebugEnabled()) {
            FORMAT_LOG.debug("Outbound HTTP request format: method={}, uri={}, headers={}, body={}", request.getMethod(),
                    request.getURI(), maskedHeaders(request.getHeaders()), body.length == 0 ? "<empty>" : new String(body, StandardCharsets.UTF_8));
        }

        ClientHttpResponse response = execution.execute(request, body);
        TRAFFIC_LOG.info("Inbound HTTP response: {} {} -> {}", request.getMethod(), request.getURI(), response.getStatusCode());
        if (FORMAT_LOG.isDebugEnabled()) {
            FORMAT_LOG.debug("Inbound HTTP response format: status={}, headers={}", response.getStatusCode(),
                    maskedHeaders(response.getHeaders()));
        }
        return response;
    }

    private Map<String, Object> maskedHeaders(HttpHeaders headers) {
        Map<String, Object> values = new LinkedHashMap<>();
        headers.forEach((name, value) -> values.put(name,
                HttpHeaders.AUTHORIZATION.equalsIgnoreCase(name) || "cookie".equalsIgnoreCase(name)
                        ? "[REDACTED]" : value));
        return values;
    }
}
