package com.github.cvscharles.crypto_portfolio_api.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Map;

@Getter
@AllArgsConstructor
public class PortfolioValueResponse {
    private final double totalValue;
    private final Map<String, Double> holdingsValue;
}
   
