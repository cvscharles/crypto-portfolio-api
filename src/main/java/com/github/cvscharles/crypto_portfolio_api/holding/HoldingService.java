package com.github.cvscharles.crypto_portfolio_api.holding;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HoldingService {

       //return hard coded list of holdings
    public static List<Holding> getHoldings() {
    return List.of(Holding.bitcoinHolding);}

    //method to update quantity of holding
    public static void updateHolding(Holding name, Holding quantity) {
        
    }

    //method to delete holding record
    public static void deleteHolding(Holding name) {
        
    }
}
    
