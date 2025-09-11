package com.github.cvscharles.crypto_portfolio_api.exception;

public class HoldingNotFoundException extends RuntimeException {
    
    public HoldingNotFoundException(String name) {
        super("Holding with name '" + name + "' was not found.");
    }
}
