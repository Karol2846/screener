package com.stock.screener.domain.factory;

import com.stock.screener.domain.model.Stock;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractFactory {

    private final EntityManager entityManager;

    public Stock getReference(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            return null;
        }
        return entityManager.getReference(Stock.class, symbol);
    }
}
