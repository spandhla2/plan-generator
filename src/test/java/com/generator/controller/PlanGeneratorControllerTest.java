package com.generator.controller;

import com.generator.AbstractTests;
import com.generator.model.responses.RepaymentPlan;
import com.generator.service.IPlanGeneratorService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@WebMvcTest(value = PlanGeneratorController.class, secure = false)
public class PlanGeneratorControllerTest extends AbstractTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPlanGeneratorService planGeneratorService;

    private List<RepaymentPlan> repaymentPlanMock;
    private String loanPayloadJson;
    private String expectedValue;
    private String responseContent;
    private Integer expectedStatus;
    private Integer actualStatus;

    @Before
    public void setUp() {
        givenInitialRepaymentPlanList();
        givenExpectedReturnValue();
    }

    @After
    public void tearDown() {
        resetFieldValues();
    }

    @Test
    public void testGeneratePlan() throws Exception {
        givenInitialJsonPayload();
        givenExpectedHTTPStatus(HttpStatus.OK.value());

        whenPlanGeneratorServiceMockIsCalled();
        whenGeneratePlanEndPointIsCalled();

        thenResultHTTPStatusShouldBe(expectedStatus, actualStatus);
        thenResultShouldBe(expectedValue, responseContent);
    }

    @Test
    public void testGeneratePlanWhenPayloadIsInValid() throws Exception {
        givenInitialInValidJsonPayload();
        givenExpectedHTTPStatus(HttpStatus.BAD_REQUEST.value());

        whenPlanGeneratorServiceMockIsCalled();
        whenGeneratePlanEndPointIsCalled();

        thenResultHTTPStatusShouldBe(expectedStatus, actualStatus);
    }

    /* To add more validation tests */

    private void thenResultShouldBe(String expected, String actual) {
        assertEquals(expected, actual);
    }

    private void thenResultHTTPStatusShouldBe(Integer expected, Integer actual) {
        assertEquals(expected, actual);
    }

    private void whenPlanGeneratorServiceMockIsCalled() {
        Mockito.when(planGeneratorService.generateRepaymentPlan(Mockito.anyObject())).thenReturn(repaymentPlanMock);
    }

    private void whenGeneratePlanEndPointIsCalled() throws Exception{
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/generate-plan")
                                                                            .content(loanPayloadJson)
                                                                            .contentType(MediaType.APPLICATION_JSON)
                                                                            .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        actualStatus = response.getStatus();
        responseContent = response.getContentAsString();
    }

    private void givenExpectedHTTPStatus(int httpStatus) {
        expectedStatus = httpStatus;
    }

    private void givenInitialRepaymentPlanList() {
        repaymentPlanMock = Arrays.asList(
                new RepaymentPlan(BigDecimal.valueOf(219.36),
                        LocalDateTime.ofInstant(Instant.parse("2018-01-01T00:00:01Z"), ZoneId.of(ZoneOffset.UTC.getId())),
                        BigDecimal.valueOf(5000.00), BigDecimal.valueOf(20.83),
                        BigDecimal.valueOf(198.53), BigDecimal.valueOf(4801.47))
        );
    }

    private void givenInitialJsonPayload() {
        loanPayloadJson = "{\"loanAmount\": \"5000\",\"nominalRate\": \"5.0\",\"duration\": \"1\"," +
                            "\"startDate\": \"2018-01-01T00:00:01Z\" }";
    }

    private void givenInitialInValidJsonPayload() {
        loanPayloadJson = "{\"loanAmount\": \"5000\",\"nominalRate\": \"0.0\",\"duration\": \"24\"," +
                "\"startDate\": \"2018-01-01T00:00:01Z\" }";
    }

    private void givenExpectedReturnValue() {
        expectedValue = "[{\"borrowerPaymentAmount\":219.36,\"date\":\"2018-01-01T00:00:01\"," +
                        "\"initialOutstandingPrincipal\":5000.0,\"interest\":20.83,\"principal\":198.53," +
                        "\"remainingOutstandingPrincipal\":4801.47}]";
    }

    private void resetFieldValues() {
        if(loanPayloadJson != null) {
            loanPayloadJson = null;
        }

        if(responseContent != null) {
            responseContent = null;
        }

        if(expectedStatus != null) {
            expectedStatus = null;
        }

        if(actualStatus != null) {
            actualStatus = null;
        }
    }
}