package com.github.cvscharles.crypto_portfolio_api.dto;

import java.time.LocalDate;

public record HoldingRequest(
        String name,
        double quantity,
        LocalDate purchaseDate
) {}
