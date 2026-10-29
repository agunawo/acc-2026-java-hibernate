package bootcamp.hibernate_practical.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.time.Duration;

// A secured OUTBOUND call: sends a bearer token (kept outside source code) to an
// external service, and handles external failures clearly instead of leaking them.
@Service
public class ExternalBookService {

    private final RestClient restClient;

    public ExternalBookService(@Value("${external.api.base-url}") String baseUrl,
                               @Value("${external.api.token}") String token) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(5));
        factory.setReadTimeout(Duration.ofSeconds(5));

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + token)
                .requestFactory(factory)
                .build();
    }

    public String fetchExternalBooks() {
        try {
            return restClient.get()
                    .uri("/books")
                    .retrieve()
                    .body(String.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("External service rejected the request (4xx): " + e.getStatusCode());
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("External service error (5xx): " + e.getStatusCode());
        } catch (ResourceAccessException e) {
            throw new RuntimeException("External service unavailable (timeout/connection).");
        }
    }
}
