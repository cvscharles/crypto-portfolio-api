package com.github.cvscharles.crypto_portfolio_api.holding;

import com.github.cvscharles.crypto_portfolio_api.dto.HoldingRequest;
import com.github.cvscharles.crypto_portfolio_api.dto.HoldingResponse;
import com.github.cvscharles.crypto_portfolio_api.dto.PortfolioValueResponse;
import com.github.cvscharles.crypto_portfolio_api.dto.UpdateHolding;
import com.github.cvscharles.crypto_portfolio_api.valuation.ValuationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/holdings")
public class HoldingController {
    
    private final HoldingService holdingService;
    private final ValuationService valuationService;

    //dependencies injected within constructor
    public HoldingController(HoldingService holdingService, ValuationService valuationService) {
        this.holdingService = holdingService;
        this.valuationService = valuationService;
    }

    //swagger annotations
    @Operation(
        summary = "Get all holdings",
        description = "Retrieves a list of all holdings in the portfolio."
    )
    @ApiResponse(
        responseCode = "200",
        description = "List of holdings retrieved successfully",
        content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = HoldingResponse.class))
    )
    //endpoint that maps to service method to return all holdings
    @GetMapping("/allholdings")
    public List<HoldingResponse> getAllHoldings() {
        return holdingService.getAllHoldings().stream()
                .map(this::toResponse)
                .toList();
    }

    //swagger annotations
    @Operation(
        summary = "Get total portfolio value",
        description = "Calculates the total value of the portfolio using current market prices."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Portfolio value calculated successfully",
        content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PortfolioValueResponse.class))
    )
    //endpoint that maps to service method to return total value of portfolio
    @GetMapping("/portfolio")
    public PortfolioValueResponse getPortfolioValue() {
        return valuationService.calculatePortfolioValue();
    }

    //swagger annotations
    @Operation(
        summary = "Get holding by name",
        description = "Retrieves a single holding by its name (case-insensitive). Returns 404 if not found."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Holding found",
        content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = HoldingResponse.class))
    )
    @ApiResponse(responseCode = "404", description = "Holding not found")
    //endpoint that maps to service method to return a holding by its name
    @GetMapping("/{name}")
    public HoldingResponse getHolding(
            @Parameter(description = "The name of the holding to retrieve", example = "Bitcoin")
            @PathVariable String name) {
        return toResponse(holdingService.getHoldingByName(name));
    }

    //swagger annotations
    @Operation(
        summary = "Create a new holding",
        description = "Adds a new holding to the portfolio."
    )
    @ApiResponse(responseCode = "201", description = "Holding created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    //endpoint that maps to service method to create a new holding
    @PostMapping("/create")
    public String createHolding(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Holding details",
                required = true,
                content = @Content(schema = @Schema(implementation = HoldingRequest.class))
            )
            @RequestBody HoldingRequest request) {
        Holding holding = new Holding(null, request.name(), request.quantity(), request.purchaseDate());
        holdingService.createHolding(holding);
        return "Holding created successfully";
    }

    //swagger annotations
    @Operation(
        summary = "Update an existing holding",
        description = "Updates the quantity of an existing holding by name."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Holding updated successfully",
        content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = HoldingResponse.class))
    )
    @ApiResponse(responseCode = "404", description = "Holding not found")
    //endpoint that maps to service method to update a specific holding
    @PostMapping("/updateholding")
    public HoldingResponse updateHolding(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated holding details",
                required = true,
                content = @Content(schema = @Schema(implementation = UpdateHolding.class))
            )
            @RequestBody UpdateHolding updateRequest) {
        return toResponse(holdingService.updateHolding(updateRequest.name(), updateRequest.quantity()));
    }

    //swagger annotations
    @Operation(
        summary = "Delete a holding by name",
        description = "Deletes a holding from the portfolio by its name (case-insensitive)."
    )
    @ApiResponse(responseCode = "200", description = "Holding deleted successfully")
    @ApiResponse(responseCode = "404", description = "Holding not found")
    //endpoint that maps to service method to delete a holding
    @DeleteMapping("/deleteholding")
    public String deleteHolding(
            @Parameter(description = "The name of the holding to delete", example = "Ethereum")
            @RequestParam String name) {
        holdingService.deleteHolding(name);
        return "Holding deleted successfully";
    }

    //method that maps the output to the dto
    private HoldingResponse toResponse(Holding holding) {
        return new HoldingResponse(
                holding.getName(),
                holding.getQuantity(),
                holding.getPurchaseDate());
    }
}
