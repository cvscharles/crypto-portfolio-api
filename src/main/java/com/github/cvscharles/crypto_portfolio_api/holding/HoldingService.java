package com.github.cvscharles.crypto_portfolio_api.holding;

import org.springframework.stereotype.Service;
import com.github.cvscharles.crypto_portfolio_api.exception.DuplicateHoldingException;
import com.github.cvscharles.crypto_portfolio_api.exception.HoldingNotFoundException;
import com.github.cvscharles.crypto_portfolio_api.exception.SameQuantityException;
import java.util.List;

@Service
public class HoldingService {

    private final HoldingRepository holdingRepository;
    
    //dependency injected within constructor
    public HoldingService(HoldingRepository holdingRepository) {
        this.holdingRepository = holdingRepository;
    }

    //method that creates and saves a new holding record in the database as long as it is unique
    public Holding createHolding(Holding holding) {
        if (holdingRepository.existsByNameIgnoreCase(holding.getName())) {
            throw new DuplicateHoldingException(holding.getName());
        }
        return holdingRepository.save(holding);
    }

    //method that returns a list of all holding records within the database
    public List<Holding> getAllHoldings() {
        return holdingRepository.findAll();
    }

    //method that filters the database and returns a specific holding by its name
    public Holding getHoldingByName(String name) {
        return holdingRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new HoldingNotFoundException(name));
    }

    //method that updates an existing holding's quantity
    public Holding updateHolding(String name, double quantity) {
        Holding existing = getHoldingByName(name);
        if (Double.compare(existing.getQuantity(), quantity) == 0) {
            throw new SameQuantityException(
                    "New quantity for " + name + " is the same as the current quantity: " + quantity);
        }
        existing.setQuantity(quantity);
        return holdingRepository.save(existing);
    }

    //method that deletes a holding by its name
    public void deleteHolding(String name) {
        Holding existing = getHoldingByName(name);
        holdingRepository.delete(existing);
    }
}
