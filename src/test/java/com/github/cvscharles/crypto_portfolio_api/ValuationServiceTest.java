package com.github.cvscharles.crypto_portfolio_api;

import com.github.cvscharles.crypto_portfolio_api.dto.PortfolioValueResponse;
import com.github.cvscharles.crypto_portfolio_api.holding.Holding;
import com.github.cvscharles.crypto_portfolio_api.holding.HoldingService;
import com.github.cvscharles.crypto_portfolio_api.prices.PriceService;
import com.github.cvscharles.crypto_portfolio_api.valuation.ValuationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValuationServiceTest {

    @Mock
    private PriceService priceService;

    @Mock
    private HoldingService holdingService;

    @InjectMocks
    private ValuationService valuationService;

    @Test
    void calculatePortfolioValue_withSingleHolding() {
        // Arrange
        Holding btcHolding = new Holding(
                1L,                // id
                "BTC",             // name
                2.0,               // quantity
                LocalDate.of(2025, 6, 1) // purchaseDate
        );

        when(holdingService.getAllHoldings()).thenReturn(List.of(btcHolding));
        when(priceService.getCurrentPrice("BTC")).thenReturn(50_000.0);

        // Act
        PortfolioValueResponse response = valuationService.calculatePortfolioValue();

        // Assert
        assertEquals(100_000.0, response.getTotalValue());
        assertEquals(100_000.0, response.getHoldingsValue().get("BTC"));
    }

    @Test
    void calculatePortfolioValue_withMultipleHoldings() {
        // Arrange
        Holding btc = new Holding(1L, "BTC", 1.5, LocalDate.of(2025, 6, 1));
        Holding eth = new Holding(2L, "ETH", 10.0, LocalDate.of(2025, 6, 1));
        Holding doge = new Holding(3L, "DOGE", 1000.0, LocalDate.of(2025, 6, 1));

        when(holdingService.getAllHoldings()).thenReturn(List.of(btc, eth, doge));

        when(priceService.getCurrentPrice("BTC")).thenReturn(50_000.0);
        when(priceService.getCurrentPrice("ETH")).thenReturn(3_000.0);
        when(priceService.getCurrentPrice("DOGE")).thenReturn(0.25);

        // Act
        PortfolioValueResponse response = valuationService.calculatePortfolioValue();

        // Assert
        assertEquals(105_250.0, response.getTotalValue());
        assertEquals(75_000.0, response.getHoldingsValue().get("BTC"));
        assertEquals(30_000.0, response.getHoldingsValue().get("ETH"));
        assertEquals(250.0, response.getHoldingsValue().get("DOGE"));
    }

    @Test
    void calculatePortfolioValue_withNoHoldings_returnsZeroValues() {
        // Arrange
        when(holdingService.getAllHoldings()).thenReturn(List.of());

        // Act
        PortfolioValueResponse response = valuationService.calculatePortfolioValue();

        // Assert
        assertEquals(0.0, response.getTotalValue());
        assertEquals(0, response.getHoldingsValue().size());
    }
}
