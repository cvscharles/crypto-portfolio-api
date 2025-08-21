package com.github.cvscharles.crypto_portfolio_api.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomCoinGeckoService {

    private final RestTemplate restTemplate = new RestTemplate();

    //method to fetch live bitcoin price using coingecko api 
    public String getBitcoinPrice() {
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd";
        return restTemplate.getForObject(url, String.class);
    }
}
