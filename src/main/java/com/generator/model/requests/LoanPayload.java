package com.generator.model.requests;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoanPayload {

    @NotNull(message = "Loan amount is mandatory")
    @DecimalMin(value = "0.1", inclusive = true, message = "Minimum loan amount is 0.10")
    @Digits(integer = 15, fraction = 2, message = "Loan amount contains max of fifteen integers and two decimals")
    private BigDecimal loanAmount;

    @NotNull(message = "Nominal rate is mandatory")
    @DecimalMin(value = "0.1", inclusive = true, message = "Minimum nominal rate is 0.10")
    @Digits(integer = 6, fraction = 2, message = "Nominal rate contains max of six integers and two decimals")
    private BigDecimal nominalRate;

    @NotNull(message = "Duration is mandatory")
    @Min(value = 1, message = "Duration cannot be less than 1 month")
    private Integer duration;

    @NotNull(message = "Start date is mandatory")
    private LocalDateTime startDate;

    public BigDecimal getLoanAmount()
    {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount)
    {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getNominalRate()
    {
        return nominalRate;
    }

    public void setNominalRate(BigDecimal nominalRate)
    {
        this.nominalRate = nominalRate;
    }

    public Integer getDuration()
    {
        return duration;
    }

    public void setDuration(Integer duration)
    {
        this.duration = duration;
    }

    public LocalDateTime getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate)
    {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "LoanPayload{" +
                "loanAmount=" + loanAmount +
                ", nominalRate=" + nominalRate +
                ", duration=" + duration +
                ", startDate=" + startDate +
                '}';
    }
}
