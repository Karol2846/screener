package com.stock.screener.domain.mapper;

import com.stock.screener.domain.model.CompoundId;
import com.stock.screener.domain.model.PriceHistory;
import com.stock.screener.application.port.command.CurrentPriceCommand;
import com.stock.screener.application.port.command.MovingAveragesCommand;
import com.stock.screener.domain.model.Stock;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceHistoryMapper {

    private final StockReference stockReference;

    public void update(PriceHistory priceHistory, CurrentPriceCommand command) {
        if ( command == null ) {
            return;
        }

        priceHistory.setCurrentPrice(command.currentPrice());
    }

    public void update(PriceHistory priceHistory, MovingAveragesCommand command) {
        if ( command == null ) {
            return;
        }

        priceHistory.setAveragePrice50Days(command.average50Days());
        priceHistory.setAveragePrice100Days(command.average100Days());
        priceHistory.setAveragePrice200Days(command.average200Days());
    }

    public PriceHistory from(CurrentPriceCommand command) {
        if (command == null) {
            return null;
        }
        Stock ref = stockReference.getReference(command.ticker());

        return PriceHistory.builder()
                .id(CompoundId.builder()
                        .symbol(command.ticker())
                        .createdAt(LocalDate.now())
                        .build())
                .currentPrice(command.currentPrice())
                .stock(ref)
                .build();
    }

    public PriceHistory from(MovingAveragesCommand command) {
        if (command == null) {
            return null;
        }
        Stock ref = stockReference.getReference(command.ticker());

        return PriceHistory.builder()
                .id(CompoundId.builder()
                        .symbol(command.ticker())
                        .createdAt(LocalDate.now())
                        .build())
                .averagePrice50Days(command.average50Days())
                .averagePrice100Days(command.average100Days())
                .averagePrice200Days(command.average200Days())
                .stock(ref)
                .build();
    }
}
