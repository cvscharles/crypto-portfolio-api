package com.github.cvscharles.crypto_portfolio_api.exception;

public class DuplicateHoldingException extends RuntimeException {
    
    public DuplicateHoldingException(String name) {
        super("Holding with name '" + name + "' already exists.");
    }
}
