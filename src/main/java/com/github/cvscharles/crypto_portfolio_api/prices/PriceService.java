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
            ParameterizedTypeReference<Map<String, Map<String, Double>>> typeRef = new ParameterizedTypeReference<>() {
            };
            String cryptoLowerCase = cryptoId.toLowerCase();
            Map<String, Map<String, Double>> response = webClient.get()
                    .uri("/simple/price?ids="+cryptoLowerCase+"&vs_currencies=gbp")
                    .header("x-cg-demo-api-key", "CG-tZJuqzdX3vBzK4Aj2ueEDx7w")
                    .retrieve()
                    .bodyToMono(typeRef)
                    .block();

            if (response == null || !response.containsKey(cryptoLowerCase)) {
                throw new PriceNotFoundException("Current price not found for crypto: " + cryptoLowerCase);
            }

            return response.get(cryptoLowerCase).get("gbp");
        } catch (Exception e) {
            throw new PriceNotFoundException("Error fetching current price for crypto: " + cryptoId);
        }
    }
}
