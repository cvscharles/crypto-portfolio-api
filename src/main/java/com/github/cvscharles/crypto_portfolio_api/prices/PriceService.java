package com.github.cvscharles.crypto_portfolio_api.prices;

import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.github.cvscharles.crypto_portfolio_api.exception.PriceNotFoundException;

@Service
public class PriceService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.coingecko.com/api/v3")
            .build();

    /**
     * Get the current price (GBP) for a crypto asset using its CoinGecko ID.
     * Throws PriceNotFoundException if not available.
     */
    public double getCurrentPrice(String cryptoId) {
        try {
            ParameterizedTypeReference<Map<String, Map<String, Double>>> typeRef =
                    new ParameterizedTypeReference<>() {};

            Map<String, Map<String, Double>> response = webClient.get()
                    .uri("/simple/price?ids={id}&vs_currencies=gbp", cryptoId)
                    .retrieve()
                    .bodyToMono(typeRef)
                    .block();

            if (response == null || !response.containsKey(cryptoId)) {
                throw new PriceNotFoundException("Current price not found for crypto: " + cryptoId);
            }

            return response.get(cryptoId).getOrDefault("gbp",
                    throwAndReturn("Current GBP price missing for crypto: " + cryptoId));
        } catch (Exception e) {
            throw new PriceNotFoundException("Error fetching current price for crypto: " + cryptoId);
        }
    }       

    private double throwAndReturn(String message) {
        throw new PriceNotFoundException(message);
    }
}
