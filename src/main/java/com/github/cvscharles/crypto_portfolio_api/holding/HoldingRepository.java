package com.github.cvscharles.crypto_portfolio_api.holding;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoldingRepository extends JpaRepository<Holding, Long> {

    Optional<Holding> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
    void deleteByNameIgnoreCase(String name);
    
}
