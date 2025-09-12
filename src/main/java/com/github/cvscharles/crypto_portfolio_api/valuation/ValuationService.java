package com.github.cvscharles.crypto_portfolio_api.valuation;

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
    private final HoldingService holdingService;

    public ValuationService(PriceService priceService, HoldingService holdingService) {
        this.priceService = priceService;
        this.holdingService = holdingService;
    }

    public PortfolioValueResponse calculatePortfolioValue() {
        Map<String, Double> holdingsValue = new HashMap<>();
        double totalValue = 0.0;

        for (Holding record : holdingService.getAllHoldings()) {
            String name = record.getName();
            double currentPrice = priceService.getCurrentPrice(name);
            double currentValue = record.getQuantity() * currentPrice;

            holdingsValue.put(name, currentValue);
            totalValue += currentValue;

        }

        return new PortfolioValueResponse(
                totalValue,
                holdingsValue);
    }

}
