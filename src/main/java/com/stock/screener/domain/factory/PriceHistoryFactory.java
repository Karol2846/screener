package com.stock.screener.domain.factory;

import com.stock.screener.application.port.command.CurrentPriceCommand;
import com.stock.screener.application.port.command.MovingAveragesCommand;
import com.stock.screener.domain.mapper.PriceHistoryMapper;
import com.stock.screener.domain.model.CompoundId;
import com.stock.screener.domain.model.PriceHistory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class PriceHistoryFactory extends AbstractFactory {

    private final PriceHistoryMapper mapper;

    public PriceHistoryFactory(EntityManager entityManager, PriceHistoryMapper mapper) {
        super(entityManager);
        this.mapper = mapper;
    }

    public PriceHistory from(CurrentPriceCommand command) {
        PriceHistory priceHistory = mapper.from(command);

        priceHistory.setId(CompoundId.forSymbolWithActualDate(command.ticker()));
        priceHistory.setStock(getReference(command.ticker()));

        return priceHistory;
    }

    public PriceHistory from(MovingAveragesCommand command) {
        PriceHistory priceHistory = mapper.from(command);

        priceHistory.setId(CompoundId.forSymbolWithActualDate(command.ticker()));
        priceHistory.setStock(getReference(command.ticker()));

        return priceHistory;
    }

    public void update(PriceHistory priceHistory, CurrentPriceCommand command) {
        mapper.update(priceHistory, command);
    }

    public void update(PriceHistory priceHistory, MovingAveragesCommand command) {
        mapper.update(priceHistory, command);
    }
}
