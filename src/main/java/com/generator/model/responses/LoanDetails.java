package com.generator.model.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoanDetails {

    private final BigDecimal loanAmount;
    private final BigDecimal nominalRate;
    private final BigDecimal annuity;
    private final LocalDateTime date;
    private Integer duration;

    public LoanDetails(BigDecimal loanAmount, BigDecimal nominalRate, BigDecimal annuity, LocalDateTime date, Integer duration) {
        this.loanAmount = loanAmount;
        this.nominalRate = nominalRate;
        this.annuity = annuity;
        this.date = date;
        this.duration = duration;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public BigDecimal getNominalRate() {
        return nominalRate;
    }

    public BigDecimal getAnnuity() {
        return annuity;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Integer getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "LoanDetails{" +
                "loanAmount=" + loanAmount +
                ", nominalRate=" + nominalRate +
                ", annuity=" + annuity +
                ", date=" + date +
                ", duration=" + duration +
                '}';
    }
}
