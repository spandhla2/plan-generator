package com.generator.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PlanCalculator implements IPlanCalculator {
    private static final Integer SCALE_TWO = 2;
    private static final Integer SCALE_NINE = 9;
    private static final Integer ROUND_FLOOR = BigDecimal.ROUND_FLOOR;
    private static final Integer ONE_HUNDRED = 100;
    private static final Integer MONTHS_IN_YEAR = 12;

    public BigDecimal initialAnnuityAmount(Integer duration, BigDecimal nominalRate, BigDecimal loanAmount) {
        BigDecimal one = BigDecimal.valueOf(1.0);

        BigDecimal periodicRate = nominalRate.divide(BigDecimal.valueOf(MONTHS_IN_YEAR), SCALE_NINE, ROUND_FLOOR);

        BigDecimal periodicRateValue = periodicRate.divide(BigDecimal.valueOf(ONE_HUNDRED), SCALE_NINE, ROUND_FLOOR);

        BigDecimal power = one.divide((one.add(periodicRateValue)).pow(duration), SCALE_NINE, ROUND_FLOOR);

        BigDecimal divisor = (one.subtract(power));

        BigDecimal initialAnnuity = (loanAmount.multiply(periodicRateValue)).divide(divisor, SCALE_TWO, BigDecimal.ROUND_CEILING);

        return initialAnnuity;
    }

    public BigDecimal annuityAmount(BigDecimal principal, BigDecimal interest) {
        return principal.add(interest);
    }

    public BigDecimal interest(BigDecimal nominalRate,  BigDecimal initialOutstandingPrincipal, Integer daysInMonth, Integer daysInYear) {
        return nominalRate.multiply(initialOutstandingPrincipal)
                          .multiply(BigDecimal.valueOf(daysInMonth))
                          .divide(BigDecimal.valueOf(daysInYear)
                                  .multiply(BigDecimal.valueOf(ONE_HUNDRED)), SCALE_TWO, ROUND_FLOOR);
    }

    public BigDecimal principal(BigDecimal annuity, BigDecimal interest) {
        return annuity.subtract(interest);
    }

    public BigDecimal remainingOutstandingPrincipal(BigDecimal initialOutstandingPrincipal, BigDecimal principal) {
        BigDecimal amount = initialOutstandingPrincipal.subtract(principal);
        return amount.max(BigDecimal.valueOf(0.0));
    }
}
