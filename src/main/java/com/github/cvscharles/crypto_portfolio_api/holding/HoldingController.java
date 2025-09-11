package com.github.cvscharles.crypto_portfolio_api.holding;

import org.springframework.web.bind.annotation.RestController;
import com.github.cvscharles.crypto_portfolio_api.dto.HoldingRequest;
import com.github.cvscharles.crypto_portfolio_api.dto.HoldingResponse;
import com.github.cvscharles.crypto_portfolio_api.dto.PortfolioValueResponse;
import com.github.cvscharles.crypto_portfolio_api.valuation.ValuationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.List;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/holdings")

public class HoldingController {

    private final HoldingService holdingService;
    private final ValuationService valuationService;

    public HoldingController(HoldingService holdingService, ValuationService valuationService) {
        this.holdingService = holdingService;
        this.valuationService = valuationService;
    }

    @GetMapping
    public List<HoldingResponse> getAllHoldings() {
        return holdingService.getAllHoldings().stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/portfolio")
    public PortfolioValueResponse getPortfolioValue() {
        return valuationService.calculatePortfolioValue();
    }

    @GetMapping("/{name}")
    public HoldingResponse getHolding(@PathVariable String name) {
        return toResponse(holdingService.getHoldingByName(name));
    }

    @PostMapping("/create")
    public String createHolding(@RequestBody HoldingRequest request) {
        Holding holding = new Holding(request.name(), request.quantity(), request.purchaseDate());
        holdingService.createHolding(holding);
        return "Holding created successfully";
    }

    @PutMapping("/{name}")
    public HoldingResponse updateHolding(@PathVariable String name, @RequestParam double quantity) {
        return toResponse(holdingService.updateHolding(name, quantity));
    }

    @DeleteMapping("/{name}")
    public void deleteHolding(@PathVariable String name) {
        holdingService.deleteHolding(name);
    }

    private HoldingResponse toResponse(Holding holding) {
        return new HoldingResponse(
                holding.getName(),
                holding.getQuantity(),
                holding.getPurchaseDate());
    }
}
