package com.stock.screener.adapter.web.seeking.alpha.api;

import com.stock.screener.adapter.web.seeking.alpha.client.SeekingAlphaClient;
import com.stock.screener.adapter.web.seeking.alpha.model.summary.SummaryResponse;
import com.stock.screener.adapter.web.seeking.alpha.properties.SeekingAlphaProperties;
import com.stock.screener.application.exception.ListSizeExceededException;
import com.stock.screener.application.port.command.AnalystRecommendationCommand;
import com.stock.screener.application.port.command.MovingAveragesCommand;
import com.stock.screener.application.port.command.PriceTargetCommand;
import com.stock.screener.application.port.command.StockSummaryCommand;
import com.stock.screener.application.port.in.api.SeekingAlphaApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.stock.screener.adapter.web.seeking.alpha.mapper.SeekingAlphaMapper.mapToAnalystRecommendation;
import static com.stock.screener.adapter.web.seeking.alpha.mapper.SeekingAlphaMapper.mapToMovingAverages;
import static com.stock.screener.adapter.web.seeking.alpha.mapper.SeekingAlphaMapper.mapToPriceTarget;
import static com.stock.screener.adapter.web.seeking.alpha.mapper.SeekingAlphaMapper.mapToStockSummary;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeekingAlphaApiImpl implements SeekingAlphaApi {

    private final SeekingAlphaClient client;
    private final SeekingAlphaProperties properties;
    public static final String DELIMITER = ",";

    @Override
    public List<StockSummaryCommand> getStockSummary(List<String> symbols) {

        validateListSize(symbols);
        SummaryResponse summaryResponse = client.getSummary(join(symbols));

        log.info("Get Stock Summary Response: {}", summaryResponse);
        return mapToStockSummary(summaryResponse);
    }

    @Override
    public List<MovingAveragesCommand> getMovingAverages(List<String> symbols) {

        validateListSize(symbols);
        return mapToMovingAverages(client.getMovingAverage(join(symbols)));
    }

    @Override
    public List<PriceTargetCommand> getPriceTarget(List<String> tickerIds) {

        validateListSize(tickerIds);
        return mapToPriceTarget(client.getPriceTarget(join(tickerIds)));
    }

    @Override
    public List<AnalystRecommendationCommand> getAnalystRecommendation(List<String> tickerIds) {

        validateListSize(tickerIds);
        return mapToAnalystRecommendation(client.getAnalystRecommendation(join(tickerIds)));
    }


    private static String join(List<String> symbols) {
        return String.join(DELIMITER, symbols);
    }

    private void validateListSize(List<String> symbols) {
        if (symbols.size() > properties.maxListSize()) {
            throw new ListSizeExceededException("The number of symbols exceeds the maximum allowed size of " +
                    properties.maxListSize());
        }
    }
}
