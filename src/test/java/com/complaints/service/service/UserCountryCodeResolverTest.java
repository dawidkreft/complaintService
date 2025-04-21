package com.complaints.service.service;

import com.complaints.service.BaseTest;
import com.complaints.service.exception.UserCountryNotFoundException;
import okhttp3.mockwebserver.MockResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class UserCountryCodeResolverTest extends BaseTest {

    @MockBean
    private UserIpProvider userIpProvider;

    @Autowired
    private UserCountryCodeResolver userCountryCodeResolver;

    @Test
    void shouldReturnCountryCodeWhenApiReturnsSuccess() {
        doReturn("8.8.8.8").when(userIpProvider).getUserIp();
        String mockResponse = """
                {
                  "countryCode": "PL",
                  "status": "success",
                  "message": ""
                }
                """;
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponse)
                .addHeader("Content-Type", "application/json"));

        var countryCode = userCountryCodeResolver.resolveUserCountryCode();

        assertThat(countryCode).contains("PL");
    }

    @Test
    void shouldThrowExceptionWhenResponseIsFail() {
        doReturn("8.8.8.8").when(userIpProvider).getUserIp();
        String mockResponse = """
                {
                  "status": "fail",
                  "message": "invalid query"
                }
                """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponse)
                .addHeader("Content-Type", "application/json"));

        assertThatThrownBy(() -> userCountryCodeResolver.resolveUserCountryCode())
                .isInstanceOf(UserCountryNotFoundException.class)
                .hasMessageContaining("Unable to resolve country code");
    }
}
