package com.generator.controller;

import com.generator.AbstractTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlanGeneratorControllerIT extends AbstractTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private String loanPayloadJson;
    private String expectedResponse;
    private String actualResponse;

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testGeneratePlan() throws Exception {
        givenInitialJsonPayload();
        givenExpectedResponse();

        whenControllerIsCalled();

        thenResultShouldBe(expectedResponse, actualResponse);
    }

    private void thenResultShouldBe(String expected, String actual) {
        assertEquals(expected, actual);
    }

    private void whenControllerIsCalled() {
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(loanPayloadJson, headers);

        String response = restTemplate.postForObject("/generate-plan", entity, String.class);
        actualResponse = response;
    }

    private void givenExpectedResponse() {
        expectedResponse = "[{\"borrowerPaymentAmount\":1680.58,\"date\":\"2018-01-01T00:00:01\"," +
                            "\"initialOutstandingPrincipal\":5000,\"interest\":20.83,\"principal\":1659.75," +
                            "\"remainingOutstandingPrincipal\":3340.25},{\"borrowerPaymentAmount\":1680.58," +
                            "\"date\":\"2018-02-01T00:00:01\",\"initialOutstandingPrincipal\":3340.25,\"interest\":13.91," +
                            "\"principal\":1666.67,\"remainingOutstandingPrincipal\":1673.58}," +
                            "{\"borrowerPaymentAmount\":1680.58,\"date\":\"2018-03-01T00:00:01\"," +
                            "\"initialOutstandingPrincipal\":1673.58,\"interest\":6.97,\"principal\":1673.61," +
                            "\"remainingOutstandingPrincipal\":0.0}]";
    }

    private void givenInitialJsonPayload() {
        loanPayloadJson = "{\"loanAmount\": \"5000\",\"nominalRate\": \"5.0\",\"duration\": \"3\"," +
                "\"startDate\": \"2018-01-01T00:00:01Z\" }";
    }
}