package com.github.cvscharles.crypto_portfolio_api.holding;

import org.springframework.stereotype.Service;

import com.github.cvscharles.crypto_portfolio_api.exception.DuplicateHoldingException;
import com.github.cvscharles.crypto_portfolio_api.exception.HoldingNotFoundException;
import com.github.cvscharles.crypto_portfolio_api.exception.SameQuantityException;
import java.util.List;

@Service
public class HoldingService {

    private final HoldingRepository holdingRepository;

    public HoldingService(HoldingRepository holdingRepository) {
        this.holdingRepository = holdingRepository;
    }

    public Holding createHolding(Holding holding) {
        if (holdingRepository.existsByNameIgnoreCase(holding.getName())) {
            throw new DuplicateHoldingException(holding.getName());
        }
        return holdingRepository.save(holding);
    }

    public List<Holding> getAllHoldings() {
        return holdingRepository.findAll();
    }

    public Holding getHoldingByName(String name) {
        return holdingRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new HoldingNotFoundException(name));
    }

    public Holding updateHolding(String name, double quantity) {
        Holding existing = getHoldingByName(name);
        if (Double.compare(existing.getQuantity(), quantity) == 0) {
            throw new SameQuantityException(
                    "New quantity for " + name + " is the same as the current quantity: " + quantity);
        }
        existing.setQuantity(quantity);
        return holdingRepository.save(existing);
    }

    public void deleteHolding(String name) {
        Holding existing = getHoldingByName(name);
        holdingRepository.delete(existing);
    }
}
