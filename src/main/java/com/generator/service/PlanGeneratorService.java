package com.generator.service;

import com.generator.model.requests.LoanPayload;
import com.generator.model.responses.LoanDetails;
import com.generator.model.responses.RepaymentPlan;
import com.generator.util.IPlanCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PlanGeneratorService implements IPlanGeneratorService {

    private static final Logger LOGGER = LogManager.getLogger(PlanGeneratorService.class);

    private static final Integer DAYS_IN_MONTH = 30;
    private static final Integer DAYS_IN_YEAR = 360;

    @Autowired
    private IPlanCalculator planCalculator;

    private LoanDetails loanDetails;

    @Override
    public List<RepaymentPlan> generateRepaymentPlan(LoanPayload loanPayload) {
        return getRepaymentPlan(loanPayload);
    }

    private List<RepaymentPlan> getRepaymentPlan(LoanPayload loanPayload) {
        LOGGER.info("Generating repayment plan for the loan payload: " + loanPayload);

        LocalDateTime startDate = loanPayload.getStartDate();
        BigDecimal initialOutstandingPrincipal = loanPayload.getLoanAmount();
        BigDecimal nominalRate = loanPayload.getNominalRate();
        Integer duration = loanPayload.getDuration();

        BigDecimal initialAnnuity = calcInitialAnnuityAmount(duration, nominalRate, initialOutstandingPrincipal);

        LOGGER.info("Initial calculated annuity: " + initialAnnuity);

        loanDetails = mapLoanDetails(initialOutstandingPrincipal, nominalRate, initialAnnuity, startDate, duration);

        List<RepaymentPlan> repaymentPlanList = IntStream.range(0, duration)
                                                .boxed()
                                                .map(plan -> processOneMonthPlan(loanDetails))
                                                .collect(Collectors.toList());

        return repaymentPlanList;
    }

    private RepaymentPlan processOneMonthPlan(LoanDetails loanDetails) {
        LOGGER.debug("Loan Details for repayment plan: " + loanDetails);

        BigDecimal nominalRate = loanDetails.getNominalRate();
        BigDecimal initialOutstandingPrincipal = loanDetails.getLoanAmount();
        LocalDateTime startDate = loanDetails.getDate();
        LocalDateTime nextStartDate = startDate.plusMonths(1);

        BigDecimal interest = calcInterest(nominalRate, initialOutstandingPrincipal);

        BigDecimal minimumValue = interest.min(initialOutstandingPrincipal);

        BigDecimal principal = calcPrincipal(loanDetails.getAnnuity(), minimumValue);

        BigDecimal annuity = calcAnnuityAmount(principal, interest);

        BigDecimal remainingOutstandingPrincipal = calcRemainingOutstandingPrincipal(initialOutstandingPrincipal, principal);

        mapLoanDetails(remainingOutstandingPrincipal, nominalRate, annuity, nextStartDate, loanDetails.getDuration());

        return new RepaymentPlan(
                annuity, startDate, initialOutstandingPrincipal, interest, principal, remainingOutstandingPrincipal);
    }

    private BigDecimal calcInitialAnnuityAmount(Integer duration, BigDecimal nominalRate, BigDecimal loanAmount) {
        return planCalculator.initialAnnuityAmount(duration, nominalRate, loanAmount);
    }

    private BigDecimal calcInterest(BigDecimal nominalRate,  BigDecimal initialOutstandingPrincipal) {
        return planCalculator.interest(nominalRate, initialOutstandingPrincipal, DAYS_IN_MONTH, DAYS_IN_YEAR);
    }

    private BigDecimal calcPrincipal(BigDecimal annuity, BigDecimal interest) {
        return planCalculator.principal(annuity, interest);
    }

    private BigDecimal calcAnnuityAmount(BigDecimal principal, BigDecimal interest) {
        return planCalculator.annuityAmount(principal, interest);
    }

    private BigDecimal calcRemainingOutstandingPrincipal(BigDecimal initialOutstandingPrincipal, BigDecimal principal) {
        return planCalculator.remainingOutstandingPrincipal(initialOutstandingPrincipal, principal);
    }

    private LoanDetails mapLoanDetails(BigDecimal loanAmount, BigDecimal nominalRate, BigDecimal annuity,
                                            LocalDateTime startDate, Integer duration) {

        loanDetails = new LoanDetails(loanAmount, nominalRate, annuity, startDate, duration);

        return loanDetails;
    }
}
