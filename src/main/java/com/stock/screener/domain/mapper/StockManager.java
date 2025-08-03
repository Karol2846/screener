package com.stock.screener.domain.mapper;

import com.stock.screener.domain.model.Stock;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockManager {
    private final EntityManager entityManager;

    public Stock getReference(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            return null;
        }
        return entityManager.getReference(Stock.class, symbol);
    }
}
