package com.complaints.service.service.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class IpGeolocationClient {

    private final WebClient webClient;

    public Optional<String> getCountryCode(String ip) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/json/{ip}")
                        .queryParam("fields", "countryCode,status,message")
                        .build(ip))
                .retrieve()
                .bodyToMono(IpApiResponse.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100)))
                .doOnError(e -> log.error("Failed to fetch countryCode for IP {}: {}", ip, e.getMessage()))
                .mapNotNull(response -> {
                    if ("success".equalsIgnoreCase(response.status())) {
                        return response.countryCode();
                    } else {
                        log.warn("IP lookup failed: {}", response.message());
                        return null;
                    }
                })
                .onErrorResume(e -> Mono.empty())
                .blockOptional();
    }

    public record IpApiResponse(String countryCode, String status, String message) {
    }
}
