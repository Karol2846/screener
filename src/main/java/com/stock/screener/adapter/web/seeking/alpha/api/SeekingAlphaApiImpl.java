package com.stock.screener.adapter.web.seeking.alpha.api;

import com.stock.screener.adapter.web.seeking.alpha.client.SeekingAlphaClient;
import com.stock.screener.adapter.web.seeking.alpha.mapper.SeekingAlphaMapper;
import com.stock.screener.adapter.web.seeking.alpha.properties.SeekingAlphaProperties;
import com.stock.screener.application.port.command.AnalystRecommendationCommand;
import com.stock.screener.application.port.command.MovingAveragesCommand;
import com.stock.screener.application.port.command.PriceTargetCommand;
import com.stock.screener.application.port.command.StockSummaryCommand;
import com.stock.screener.application.port.in.api.SeekingAlphaApi;
import java.util.ArrayList;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeekingAlphaApiImpl implements SeekingAlphaApi {

    private final SeekingAlphaClient client;
    private final SeekingAlphaProperties properties;
    public static final String DELIMITER = ",";

    @Override
    public List<StockSummaryCommand> getStockSummaries(List<String> symbols) {
        List<StockSummaryCommand> commands = new ArrayList<>();
        loadStockSummaries(symbols, commands);
        return commands;
    }

    @Override
    public List<MovingAveragesCommand> getMovingAverages(List<String> symbols) {
        List<MovingAveragesCommand> commands = new ArrayList<>();
        loadMovingAverages(symbols, commands);
        return commands;
    }

    @Override
    public List<PriceTargetCommand> getPriceTarget(List<String> tickerIds) {
        List<PriceTargetCommand> commands = new ArrayList<>();
        loadPriceTargets(tickerIds, commands);
        return commands;
    }

    @Override
    public List<AnalystRecommendationCommand> getAnalystRecommendation(List<String> tickerIds) {
        List<AnalystRecommendationCommand> commands = new ArrayList<>();
        loadAnalystRecommendations(tickerIds, commands);
        return commands;
    }


    private void loadStockSummaries(List<String> symbols, List<StockSummaryCommand> commands) {
        loadData(symbols, commands, client::getSummary, SeekingAlphaMapper::mapToStockSummary);
    }

    private void loadMovingAverages(List<String> symbols, List<MovingAveragesCommand> commands) {
        loadData(symbols, commands, client::getMovingAverage, SeekingAlphaMapper::mapToMovingAverages);
    }

    private void loadPriceTargets(List<String> tickers, List<PriceTargetCommand> commands) {
        loadData(tickers, commands, client::getPriceTarget, SeekingAlphaMapper::mapToPriceTarget);
    }

    private void loadAnalystRecommendations(List<String> tickers, List<AnalystRecommendationCommand> commands) {
        loadData(tickers, commands, client::getAnalystRecommendation, SeekingAlphaMapper::mapToAnalystRecommendation);
    }

    private <T, K> void loadData(List<String> symbols, List<T> commands,
                                 Function<String, K> client, Function<K, List<T>> mapper) {
        for (int i = 0; i < symbols.size(); i += properties.maxListSize()) {
            List<String> part = getListPart(symbols, i);
            var clientResponse = client.apply(join(part));
            commands.addAll(mapper.apply(clientResponse));
        }
    }

    private static String join(List<String> symbols) {
        return String.join(DELIMITER, symbols);
    }

    private List<String> getListPart(List<String> newSymbols, int i) {
        return newSymbols.subList(i, Math.min(i + properties.maxListSize(), newSymbols.size()));
    }
}
