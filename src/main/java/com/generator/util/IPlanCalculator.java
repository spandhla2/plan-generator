package com.generator.util;

import java.math.BigDecimal;

public interface IPlanCalculator {

    BigDecimal initialAnnuityAmount(Integer duration, BigDecimal nominalRate, BigDecimal loanAmount);
    BigDecimal annuityAmount(BigDecimal principal, BigDecimal interest);
    BigDecimal interest(BigDecimal nominalRate,  BigDecimal initialPrincipal, Integer daysInMonth, Integer daysInYear);
    BigDecimal principal(BigDecimal annuity, BigDecimal interest);
    BigDecimal remainingOutstandingPrincipal(BigDecimal initialOutstandingPrincipal, BigDecimal principal);
}
