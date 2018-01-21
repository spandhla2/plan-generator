package com.generator.model.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RepaymentPlan {

    private final BigDecimal borrowerPaymentAmount;
    private final LocalDateTime date;
    private final BigDecimal initialOutstandingPrincipal;
    private final BigDecimal interest;
    private final BigDecimal principal;
    private final BigDecimal remainingOutstandingPrincipal;

    public RepaymentPlan(BigDecimal borrowerPaymentAmount, LocalDateTime date, BigDecimal initialOutstandingPrincipal,
                         BigDecimal interest, BigDecimal principal, BigDecimal remainingOutstandingPrincipal) {
        this.borrowerPaymentAmount = borrowerPaymentAmount;
        this.date = date;
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
        this.interest = interest;
        this.principal = principal;
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
    }

    public BigDecimal getBorrowerPaymentAmount() {
        return borrowerPaymentAmount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getInitialOutstandingPrincipal() {
        return initialOutstandingPrincipal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public BigDecimal getRemainingOutstandingPrincipal() {
        return remainingOutstandingPrincipal;
    }

    @Override
    public String toString() {
        return "RepaymentPlan{" +
                "borrowerPaymentAmount=" + borrowerPaymentAmount +
                ", date=" + date +
                ", initialOutstandingPrincipal=" + initialOutstandingPrincipal +
                ", interest=" + interest +
                ", principal=" + principal +
                ", remainingOutstandingPrincipal=" + remainingOutstandingPrincipal +
                '}';
    }
}
