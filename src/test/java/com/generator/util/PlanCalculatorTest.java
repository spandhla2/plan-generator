package com.generator.util;

import com.generator.model.responses.LoanDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.junit.Assert.assertEquals;

public class PlanCalculatorTest {

    private IPlanCalculator planCalculator;

    private LoanDetails loanDetails;
    private BigDecimal expected;
    private BigDecimal annuity;
    private BigDecimal principal;
    private BigDecimal interest;
    private BigDecimal remainingOutstandingPrincipal;

    @Before
    public void setUp() throws Exception {
        givenPlanCalculator();
    }

    @After
    public void tearDown() throws Exception {
        resetFieldValues();
    }

    @Test
    public void testInitialAnnuityAmount() throws Exception {
        givenInitialLoanDetails();
        givenExpectedReturnValue(219.36);

        whenInitialAnnuityAmountIsCalled();

        thenResultShouldBe(expected, annuity);
    }

    @Test
    public void testAnnuityAmount() throws Exception {
        givenInitialInterest();
        givenInitialPrincipal();
        givenExpectedReturnValue(219.36);

        whenAnnuityAmountIsCalled();

        thenResultShouldBe(expected, annuity);
    }

    @Test
    public void testInterest() throws Exception {
        givenInitialLoanDetails();
        givenExpectedReturnValue(20.83);

        whenInterestIsCalled();

        thenResultShouldBe(expected, interest);
    }

    @Test
    public void testPrincipal() throws Exception {
        givenInitialLoanDetails();
        givenInitialInterest();
        givenExpectedReturnValue(198.53);

        whenPrincipalIsCalled();

        thenResultShouldBe(expected, principal);
    }

    @Test
    public void testRemainingOutstandingPrincipal() throws Exception {
        givenInitialLoanDetails();
        givenInitialPrincipal();
        givenExpectedReturnValue(4801.47);

        whenRemainingOutstandingPrincipalIsCalled();

        thenResultShouldBe(expected, remainingOutstandingPrincipal);
    }

    private void thenResultShouldBe(BigDecimal expected, BigDecimal actual) {
        assertEquals(expected, actual);
    }

    private void whenInitialAnnuityAmountIsCalled() {
        annuity = planCalculator.initialAnnuityAmount(loanDetails.getDuration(),
                loanDetails.getNominalRate(), loanDetails.getLoanAmount());
    }

    private void whenAnnuityAmountIsCalled() {
        annuity = planCalculator.annuityAmount(principal, interest);
    }

    private void whenInterestIsCalled() {
        interest = planCalculator.interest(loanDetails.getNominalRate(), loanDetails.getLoanAmount(),
                                        30, 360);
    }

    private void whenPrincipalIsCalled() {
        principal = planCalculator.principal(loanDetails.getAnnuity(), interest);
    }

    private void whenRemainingOutstandingPrincipalIsCalled() {
        remainingOutstandingPrincipal = planCalculator.remainingOutstandingPrincipal(
                                                    loanDetails.getLoanAmount(), principal);
    }

    private void givenPlanCalculator() {
        planCalculator = new PlanCalculator();
    }

    private void givenExpectedReturnValue(Double expectedValue) {
        expected = BigDecimal.valueOf(expectedValue);
    }

    private void givenInitialInterest() {
        interest = BigDecimal.valueOf(20.83);
    }

    private void givenInitialPrincipal() {
        principal = BigDecimal.valueOf(198.53);
    }

    private void givenInitialLoanDetails() {
        loanDetails = new LoanDetails(BigDecimal.valueOf(5000.00), BigDecimal.valueOf(5.0),BigDecimal.valueOf(219.36),
                        LocalDateTime.ofInstant(Instant.parse("2018-01-01T00:00:01Z"), ZoneId.of(ZoneOffset.UTC.getId())),
                        24);
    }

    private void resetFieldValues() {

        if(loanDetails != null) {
            loanDetails = null;
        }

        if(expected != null) {
            expected = null;
        }

        if(annuity != null) {
            annuity = null;
        }

        if(principal != null) {
            principal = null;
        }

        if(interest != null) {
            interest = null;
        }

        if(remainingOutstandingPrincipal != null) {
            remainingOutstandingPrincipal = null;
        }
    }
}