package com.generator.controller;

import com.generator.model.requests.LoanPayload;
import com.generator.model.responses.RepaymentPlan;
import com.generator.service.IPlanGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import javax.validation.Valid;
import java.util.List;

@Api(value = "PlanGeneratorController", description = "To Generate Plan Controller")
@RestController
public class PlanGeneratorController {

    @Autowired
    private IPlanGeneratorService planGeneratorService;

    @ApiOperation(value = "Return repayment plan", tags = "repayment plan", response = Json.class)
    @PostMapping("/generate-plan")
    public List<RepaymentPlan> generatePlan(@Valid @RequestBody LoanPayload loanPayload) {
        return planGeneratorService.generateRepaymentPlan(loanPayload);
    }
}
