package com.generator.service;

import com.generator.AbstractTests;
import com.generator.model.requests.LoanPayload;
import com.generator.model.responses.RepaymentPlan;
import com.generator.util.IPlanCalculator;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class PlanGeneratorServiceTest extends AbstractTests {

    @MockBean
    private IPlanCalculator planCalculator;

    @Autowired
    private IPlanGeneratorService planGeneratorService;

    private LoanPayload loanPayload;
    private List<RepaymentPlan> expectedRepaymentPlan;
    private List<RepaymentPlan> actualRepaymentPlan;

    @Test
    public void testGenerateRepaymentPlan() throws Exception {
        givenInitialLoanPayload();
        givenExpectedResult();

        whenPlanCalculatorMockIsCalled();
        whenGetRepaymentPlanIsCalled();

        thenResultShouldBe(expectedRepaymentPlan, actualRepaymentPlan);
    }

    private void thenResultShouldBe(List<RepaymentPlan> expected, List<RepaymentPlan> actual) {
        assertEquals(expected.toString(), actual.toString());
    }

    private void whenGetRepaymentPlanIsCalled() {
        actualRepaymentPlan = planGeneratorService.generateRepaymentPlan(loanPayload);
    }

    private void whenPlanCalculatorMockIsCalled() {
        Mockito.when(planCalculator.initialAnnuityAmount(Mockito.anyInt(), Mockito.anyObject(), Mockito.anyObject()))
                .thenReturn(BigDecimal.valueOf(219.36));

        Mockito.when(planCalculator.interest(Mockito.anyObject(), Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(BigDecimal.valueOf(20.83), BigDecimal.valueOf(20.0));

        Mockito.when(planCalculator.principal(Mockito.anyObject(), Mockito.anyObject()))
                .thenReturn(BigDecimal.valueOf(198.53), BigDecimal.valueOf(199.36));

        Mockito.when(planCalculator.annuityAmount(Mockito.anyObject(), Mockito.anyObject()))
                .thenReturn(BigDecimal.valueOf(219.36));

        Mockito.when(planCalculator.remainingOutstandingPrincipal(Mockito.anyObject(), Mockito.anyObject()))
                .thenReturn(BigDecimal.valueOf(4801.47), BigDecimal.valueOf(4602.11));
    }

    private void givenExpectedResult() {
        expectedRepaymentPlan = new ArrayList<>(Arrays.asList(
                new RepaymentPlan(BigDecimal.valueOf(219.36),
                        LocalDateTime.ofInstant(Instant.parse("2018-01-01T00:00:01Z"), ZoneId.of(ZoneOffset.UTC.getId())),
                        BigDecimal.valueOf(5000.00), BigDecimal.valueOf(20.83),
                        BigDecimal.valueOf(198.53), BigDecimal.valueOf(4801.47)),
                new RepaymentPlan(BigDecimal.valueOf(219.36),
                        LocalDateTime.ofInstant(Instant.parse("2018-02-01T00:00:01Z"), ZoneId.of(ZoneOffset.UTC.getId())),
                        BigDecimal.valueOf(4801.47), BigDecimal.valueOf(20.00),
                        BigDecimal.valueOf(199.36), BigDecimal.valueOf(4602.11))
        ));
    }

    private void givenInitialLoanPayload() {
        LocalDateTime startDate = LocalDateTime.ofInstant(Instant.parse("2018-01-01T00:00:01Z"),
                                                            ZoneId.of(ZoneOffset.UTC.getId()));
        loanPayload = new LoanPayload();
        loanPayload.setDuration(2);
        loanPayload.setNominalRate(BigDecimal.valueOf(5.0));
        loanPayload.setLoanAmount(BigDecimal.valueOf(5000.0));
        loanPayload.setStartDate(startDate);
    }
}