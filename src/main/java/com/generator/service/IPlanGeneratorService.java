package com.generator.service;

import com.generator.model.requests.LoanPayload;
import com.generator.model.responses.RepaymentPlan;

import java.util.List;

public interface IPlanGeneratorService {
    List<RepaymentPlan> generateRepaymentPlan(LoanPayload loanPayload);
}
