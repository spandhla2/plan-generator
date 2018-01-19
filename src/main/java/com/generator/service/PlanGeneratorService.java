package com.generator.service;

import com.generator.model.requests.LoanPayload;
import com.generator.model.responses.RepaymentPlan;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

@Service
public class PlanGeneratorService implements IPlanGeneratorService {

    private static final List<RepaymentPlan> REPAYMENT_PLAN = Arrays.asList(
                            new RepaymentPlan(BigDecimal.valueOf(219.36),
                                    LocalDateTime.ofInstant(Instant.parse("2018-01-01T00:00:01Z"), ZoneId.of(ZoneOffset.UTC.getId())),
                                    BigDecimal.valueOf(5000.00), BigDecimal.valueOf(20.83),
                                    BigDecimal.valueOf(198.53), BigDecimal.valueOf(4801.47)),
                            new RepaymentPlan(BigDecimal.valueOf(219.36),
                                    LocalDateTime.ofInstant(Instant.parse("2018-02-01T00:00:01Z"), ZoneId.of(ZoneOffset.UTC.getId())),
                                    BigDecimal.valueOf(4801.47), BigDecimal.valueOf(20.00),
                                    BigDecimal.valueOf(199.36), BigDecimal.valueOf(4638))
                    );

    @Override
    public List<RepaymentPlan> generateRepaymentPlan(LoanPayload loanPayload) {
        return REPAYMENT_PLAN;
    }
}
