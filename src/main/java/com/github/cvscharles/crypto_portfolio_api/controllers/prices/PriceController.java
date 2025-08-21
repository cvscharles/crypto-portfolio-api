package com.github.cvscharles.crypto_portfolio_api.controllers.prices;

//importing relevant modules
import org.springframework.web.bind.annotation.RestController;
import com.github.cvscharles.crypto_portfolio_api.Service.CustomCoinGeckoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

//setting base endpoint
@RestController
@RequestMapping("/api/prices")

public class PriceController {

    private final CustomCoinGeckoService coinGeckoService;

    //constructor
    public PriceController(CustomCoinGeckoService coinGeckoService) {
        this.coinGeckoService = coinGeckoService;
    }

    //mapping for get method which fetches bitcoin price
    @GetMapping("/bitcoin")
    @Operation(summary = "Get Crypto Prices", description = "Get Crypto Prices")
    @ApiResponses(value = @ApiResponse(responseCode = "200"))
    public ResponseEntity<String> getBitcoinPrice() {
        return ResponseEntity.ok(coinGeckoService.getBitcoinPrice());
    }
}
