package com.complaints.service.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class IpGeolocationWebClientConfiguration {

    @Value("${geo.api.base-url:http://ip-api.com}")
    private String baseUrl;


    @Bean
    public WebClient ipGeolocationWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
