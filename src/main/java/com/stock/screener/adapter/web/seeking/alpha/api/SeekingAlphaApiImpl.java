package com.stock.screener.adapter.web.seeking.alpha.api;

import com.stock.screener.adapter.web.seeking.alpha.client.SeekingAlphaClient;
import com.stock.screener.adapter.web.seeking.alpha.properties.SeekingAlphaProperties;
import com.stock.screener.application.exception.ListSizeExceededException;
import com.stock.screener.application.port.command.AnalystRecommendationCommand;
import com.stock.screener.application.port.command.MovingAveragesCommand;
import com.stock.screener.application.port.command.PriceTargetCommand;
import com.stock.screener.application.port.command.StockSummaryCommand;
import com.stock.screener.application.port.in.SeekingAlphaApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.stock.screener.adapter.web.seeking.alpha.mapper.SeekingAlphaMapper.mapToAnalystRecommendation;
import static com.stock.screener.adapter.web.seeking.alpha.mapper.SeekingAlphaMapper.mapToMovingAverages;
import static com.stock.screener.adapter.web.seeking.alpha.mapper.SeekingAlphaMapper.mapToPriceTarget;
import static com.stock.screener.adapter.web.seeking.alpha.mapper.SeekingAlphaMapper.mapToStockSummary;

@Service
@RequiredArgsConstructor
public class SeekingAlphaApiImpl implements SeekingAlphaApi {

    private final SeekingAlphaClient client;
    private final SeekingAlphaProperties properties;

    @Override
    public List<StockSummaryCommand> getStockSummary(String symbols) {

        validateListSize(symbols);
        return mapToStockSummary(client.getSummary(symbols));
    }

    @Override
    public List<MovingAveragesCommand> getMovingAverages(String symbols) {

        validateListSize(symbols);
        return mapToMovingAverages(client.getMovingAverage(symbols));
    }

    @Override
    public List<PriceTargetCommand> getPriceTarget(String tickerIds) {

        validateListSize(tickerIds);
        return mapToPriceTarget(client.getPriceTarget(tickerIds));
    }

    @Override
    public List<AnalystRecommendationCommand> getAnalystRecommendation(String tickerIds) {

        validateListSize(tickerIds);
        return mapToAnalystRecommendation(client.getAnalystRecommendation(tickerIds));
    }

    private void validateListSize(String symbols) {
        if (symbols.split(",").length > properties.maxListSize()) {
            throw new ListSizeExceededException("The number of symbols exceeds the maximum allowed size of " +
                    properties.maxListSize());
        }
    }
}
