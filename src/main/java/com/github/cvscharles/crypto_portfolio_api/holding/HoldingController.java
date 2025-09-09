package com.github.cvscharles.crypto_portfolio_api.holding;

import org.springframework.web.bind.annotation.RestController;
import com.github.cvscharles.crypto_portfolio_api.dto.PortfolioValueResponse;
import com.github.cvscharles.crypto_portfolio_api.valuation.ValuationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/holdings")

public class HoldingController {

    private final HoldingService holdingService;

    public HoldingController(HoldingService holdingService) {this.holdingService = holdingService;}

    //portfolio value dto method mapping
    @GetMapping("/portfolio")
    public PortfolioValueResponse getPortfolioValue(@RequestParam("username"){
        return ValuationService.calculatePortfolioValue(username);}

    //update holding method mapping
    @PostMapping("/update")
    public static void updateHolding(@RequestBody updateHolding request) {}

    //delete holding method mapping
    @DeleteMapping("/delete")
    public static void deleteHolding() {}
