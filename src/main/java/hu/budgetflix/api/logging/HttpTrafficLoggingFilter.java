package hu.budgetflix.api.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/** Logs inbound HTTP requests and outbound HTTP responses without exposing credentials. */
@Component
public class HttpTrafficLoggingFilter extends OncePerRequestFilter {

    private static final Logger TRAFFIC_LOG = LoggerFactory.getLogger("hu.budgetflix.api.traffic");
    private static final Logger FORMAT_LOG = LoggerFactory.getLogger("hu.budgetflix.api.traffic.format");
    private static final int MAX_BODY_LOG_LENGTH = 16_384;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper cachedRequest = new ContentCachingRequestWrapper(request, MAX_BODY_LOG_LENGTH);
        ContentCachingResponseWrapper cachedResponse = new ContentCachingResponseWrapper(response);
        long startedAt = System.nanoTime();

        TRAFFIC_LOG.info("HTTP request received: {} {}", request.getMethod(), request.getRequestURI());
        try {
            filterChain.doFilter(cachedRequest, cachedResponse);
        } finally {
            long durationMs = (System.nanoTime() - startedAt) / 1_000_000;
            TRAFFIC_LOG.info("HTTP response sent: {} {} -> {} ({} ms)", request.getMethod(),
                    request.getRequestURI(), cachedResponse.getStatus(), durationMs);

            if (FORMAT_LOG.isDebugEnabled()) {
                FORMAT_LOG.debug("HTTP request format: method={}, uri={}, query={}, contentType={}, headers={}, body={}",
                        request.getMethod(), request.getRequestURI(), request.getQueryString(), request.getContentType(),
                        maskedRequestHeaders(request), body(cachedRequest.getContentAsByteArray(), request.getCharacterEncoding()));
                FORMAT_LOG.debug("HTTP response format: status={}, contentType={}, headers={}, body={}",
                        cachedResponse.getStatus(), cachedResponse.getContentType(), maskedResponseHeaders(cachedResponse),
                        body(cachedResponse.getContentAsByteArray(), cachedResponse.getCharacterEncoding()));
            }
            cachedResponse.copyBodyToResponse();
        }
    }

    private Map<String, String> maskedRequestHeaders(HttpServletRequest request) {
        Map<String, String> headers = new LinkedHashMap<>();
        Enumeration<String> names = request.getHeaderNames();
        for (String name : Collections.list(names)) {
            headers.put(name, maskIfSensitive(name, request.getHeader(name)));
        }
        return headers;
    }

    private Map<String, String> maskedResponseHeaders(ContentCachingResponseWrapper response) {
        Map<String, String> headers = new LinkedHashMap<>();
        for (String name : response.getHeaderNames()) {
            headers.put(name, maskIfSensitive(name, response.getHeader(name)));
        }
        return headers;
    }

    private String maskIfSensitive(String name, String value) {
        return HttpHeaders.AUTHORIZATION.equalsIgnoreCase(name) || "cookie".equalsIgnoreCase(name)
                || "set-cookie".equalsIgnoreCase(name) ? "[REDACTED]" : value;
    }

    private String body(byte[] content, String encoding) {
        if (content.length == 0) {
            return "<empty>";
        }
        Charset charset = StringUtils.hasText(encoding) ? Charset.forName(encoding) : StandardCharsets.UTF_8;
        String value = new String(content, charset);
        return content.length >= MAX_BODY_LOG_LENGTH ? value + " [TRUNCATED]" : value;
    }
}
