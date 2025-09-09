package com.github.cvscharles.crypto_portfolio_api.valuation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.github.cvscharles.crypto_portfolio_api.prices.PriceService;
import com.github.cvscharles.crypto_portfolio_api.dto.PortfolioValueResponse;
import com.github.cvscharles.crypto_portfolio_api.holding.Holding;
import com.github.cvscharles.crypto_portfolio_api.holding.HoldingService;

@Service
public class ValuationService {

    private final PriceService priceService;

    public ValuationService(PriceService priceService) {
        this.priceService = priceService;
    }

    public PortfolioValueResponse calculatePortfolioValue() {
        Map<String, Double> holdingsValue = new HashMap<>();
        double totalValue = 0.0;

        for (Holding record : HoldingService.getHoldings()) {
            String name = Holding.getName();
            double currentPrice = priceService.getCurrentPrice(name);
            double currentValue = Holding.getQuantity() * currentPrice;

            holdingsValue.put(name, currentValue);
            totalValue += currentValue;

        }

        return new PortfolioValueResponse(
                totalValue,
                holdingsValue      
        );
    }

}
