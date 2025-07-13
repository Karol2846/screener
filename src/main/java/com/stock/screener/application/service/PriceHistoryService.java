package com.stock.screener.application.service;

import com.stock.screener.application.domain.mapper.PriceHistoryMapper;
import com.stock.screener.application.domain.model.Stock;
import com.stock.screener.application.port.command.CurrentPriceCommand;
import com.stock.screener.application.port.in.PriceHistoryUseCase;
import com.stock.screener.application.port.in.api.FinHubApi;
import com.stock.screener.application.port.out.PriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceHistoryService implements PriceHistoryUseCase {

    private final PriceHistoryMapper priceMapper;
    private final PriceHistoryRepository priceRepository;
    private final FinHubApi finHubApi;

    @Override
    public void updateCurrentPrice(List<Stock> stocks) {

    }

    @Override
    public void updateCurrentPriceBySymbol(List<String> symbols) {

        //TODO: ta metoda powinna:
        // - sprawdzać w DB, czy istnieje priceHistory dla tego tickera i daty (now())
        //      - tak -> return
        //      - nie:
        //          pobierz element z API
        //          zapisz w DB

        List<CurrentPriceCommand> currentPriceCommands = symbols.stream()
                .map(finHubApi::getCurrentPrice)
                .toList();

    }
}
