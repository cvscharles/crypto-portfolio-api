package com.github.cvscharles.crypto_portfolio_api.holding;

import org.springframework.stereotype.Service;

import com.github.cvscharles.crypto_portfolio_api.exception.DuplicateHoldingException;
import com.github.cvscharles.crypto_portfolio_api.exception.HoldingNotFoundException;
import com.github.cvscharles.crypto_portfolio_api.exception.SameQuantityException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HoldingService {

    private final List<Holding> holdings = new ArrayList<>();

    //creates a new holding as long as it doesn't exist already
    public Holding createHolding(Holding holding) {
         boolean exists = holdings.stream()
                .anyMatch(h -> h.getName().equalsIgnoreCase(holding.getName()));

        if (exists) {
            throw new DuplicateHoldingException(holding.getName());
        }

        holdings.add(holding);
        return holding;
    }

    //returns a list of all holdings
    public List<Holding> getAllHoldings() {
        return holdings;
    }

    //returns a specific holding as long as it exists
    public Holding getHoldingByName(String name) {
        return holdings.stream()
                .filter(h -> h.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new HoldingNotFoundException(name));
    }

    // method to update quantity of holding
    public Holding updateHolding(String name, double quantity) {
        Holding existing = getHoldingByName(name);
        if (Double.compare(existing.getQuantity(), quantity) == 0) {
            throw new SameQuantityException(
                    "New quantity for " + name + " is the same as the current quantity: " + quantity);
        }

        existing.setQuantity(quantity);
        return existing;
    }

    // method to delete holding record as long as it exists
    public void deleteHolding(String name) {
       boolean removed = holdings.removeIf(h -> h.getName().equalsIgnoreCase(name));

        if (!removed) {
            throw new HoldingNotFoundException(name);
        }
    }

    }

    // //return hard coded list of holdings
    // public static List<Holding> getHoldings() {
    // return List.of(Holding.bitcoinHolding);}

