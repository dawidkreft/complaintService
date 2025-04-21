package com.complaints.service;

import com.complaints.service.repository.ComplaintRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRunner.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {BaseTest.Initializer.class})
public abstract class BaseTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ComplaintRepository complaintRepository;

    protected static MockWebServer mockWebServer;

    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("integration-tests-db")
                    .withUsername("sa")
                    .withPassword("sa");


    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @BeforeAll
    public static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(12345);
        postgreSQLContainer.start();
    }

    @AfterAll
    public static void tearDown() throws IOException {
        postgreSQLContainer.stop();
        mockWebServer.shutdown();
    }

    protected String getAsJson(Object request) throws com.fasterxml.jackson.core.JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }

    protected void prepareMockServerWithSuccessResponse(String countryCode) {
        String mockResponse = String.format("""
                {
                  "countryCode": "%s",
                  "status": "success",
                  "message": ""
                }
                """, countryCode);
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponse)
                .addHeader("Content-Type", "application/json"));
    }
}
