package com.github.cvscharles.crypto_portfolio_api.holding;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Holding {
    
    private final String name;
    private double quantity;
    private final LocalDate purchaseDate;

    //hard coding first holding of bitcoin
    public static Holding bitcoinHolding = new Holding("Bitcoin", 10, LocalDate.of(2025, 9, 1));


}
