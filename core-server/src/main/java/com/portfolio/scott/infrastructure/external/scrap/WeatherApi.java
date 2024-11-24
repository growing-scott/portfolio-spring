package com.portfolio.scott.infrastructure.external.scrap;

import com.portfolio.scott.configs.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Slf4j
@Component
public class WeatherApi {

    private final WebClient webClient;

    private final AppConfig appConfig;

    public WeatherApi(AppConfig appConfig, WebClient.Builder webClientBuilder) {
        this.appConfig = appConfig;

        ConnectionProvider connectionProvider = ConnectionProvider.builder("scrapPool")
                .maxConnections(50)
                .pendingAcquireTimeout(Duration.ofSeconds(25))
                .maxIdleTime(Duration.ofSeconds(60))
                .maxLifeTime(Duration.ofMinutes(10))
                .evictInBackground(Duration.ofMinutes(5))
                .lifo()
                .build();

        HttpClient httpClient = HttpClient.create(connectionProvider)
                .protocol(HttpProtocol.HTTP11)
                .headers(headers -> {
                    headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE); // Use Netty's header methods
                    headers.set("X-API-KEY", appConfig.getScrap().getApiKey());
                })
                .responseTimeout(Duration.ofSeconds(25)); // Timeout for response

        this.webClient = webClientBuilder
                .baseUrl(appConfig.getScrap().getUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    public Mono<String> getWeather(String name, String regNo) {
        return webClient.post()
                .uri(appConfig.getScrap().getEndpoint())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<String>() {})
                .doOnError(e -> {
                    log.error("Failed Scrap", e);
                });
    }
}
