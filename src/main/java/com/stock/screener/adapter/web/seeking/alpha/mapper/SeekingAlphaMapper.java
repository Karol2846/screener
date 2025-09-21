package com.stock.screener.adapter.web.seeking.alpha.mapper;

import com.stock.screener.adapter.web.seeking.alpha.model.analyst_recomendation.AnalystRecommendationResponse;
import com.stock.screener.adapter.web.seeking.alpha.model.analyst_recomendation.AnalystRecommendationsData;
import com.stock.screener.adapter.web.seeking.alpha.model.moving_average.MovingAverage;
import com.stock.screener.adapter.web.seeking.alpha.model.moving_average.MovingAverageResponse;
import com.stock.screener.adapter.web.seeking.alpha.model.price_target.PriceTargetData;
import com.stock.screener.adapter.web.seeking.alpha.model.price_target.PriceTargetResponse;
import com.stock.screener.adapter.web.seeking.alpha.model.summary.Summary;
import com.stock.screener.adapter.web.seeking.alpha.model.summary.SummaryResponse;
import com.stock.screener.application.port.command.AnalystRecommendationCommand;
import com.stock.screener.application.port.command.MovingAveragesCommand;
import com.stock.screener.application.port.command.PriceTargetCommand;
import com.stock.screener.application.port.command.StockSummaryCommand;

import java.util.List;
import java.util.Map;

public class SeekingAlphaMapper {

    public static List<StockSummaryCommand> mapToStockSummary(SummaryResponse summary) {
        return summary.data().stream().map(SeekingAlphaMapper::mapToStockSummary).toList();
    }

    public static List<PriceTargetCommand> mapToPriceTarget(PriceTargetResponse priceTarget) {
        return priceTarget.estimates().entrySet().stream().map(SeekingAlphaMapper::mapToPriceTarget).toList();
    }

    public static List<AnalystRecommendationCommand> mapToAnalystRecommendation(
            AnalystRecommendationResponse analystRecommendation) {

        return analystRecommendation.estimates().entrySet().stream()
                .map(SeekingAlphaMapper::mapToAnalystRecommendation).toList();
    }

    public static List<MovingAveragesCommand> mapToMovingAverages(MovingAverageResponse movingAverage) {
        return movingAverage.data().stream().map(SeekingAlphaMapper::mapToMovingAverages).toList();
    }

    private static StockSummaryCommand mapToStockSummary(Summary summary) {
        var attributes = summary.attributes();
        return StockSummaryCommand.builder()
                .ticker(summary.id())
                .tickerId(summary.tickerId())
                .companyName(attributes.companyName())
                .sector(attributes.sectorname())
                .marketCap(attributes.marketCap().longValue())
                .enterpriseValue(attributes.totalEnterprise().longValue())
                .peRatio(attributes.lastClosePriceEarningsRatio())
                .pbRatio(attributes.priceBook())
                .peForward(attributes.peRatioFwd())
                .evEbitda(attributes.evEbitda())
                .evSales(attributes.evSales())
                .eps(attributes.estimateEps())
                .forwardEps3Y(attributes.dilutedEpsGrowth())
                .build();
    }

    private static MovingAveragesCommand mapToMovingAverages(MovingAverage movingAverage) {
        var attributes = movingAverage.attributes();
        return MovingAveragesCommand.builder()
                .ticker(movingAverage.id())
                .average50Days(attributes.movAvg50d())
                .average100Days(attributes.movAvg100d())
                .average200Days(attributes.movAvg200d())
                .build();
    }

    private static PriceTargetCommand mapToPriceTarget(Map.Entry<Integer, PriceTargetData> priceTargetDataEntry) {
        var priceTargetData = priceTargetDataEntry.getValue();
        return PriceTargetCommand.builder()
                .tickerId(priceTargetDataEntry.getKey())
                .priceTargetHigh(priceTargetData.latestHighPrice())
                .priceTargetConsensus(priceTargetData.latestTargetPrice())
                .priceTargetLow(priceTargetData.latestLowPrice())
                .build();
    }

    private static AnalystRecommendationCommand mapToAnalystRecommendation(
            Map.Entry<Integer, AnalystRecommendationsData> analystRecommendationsDataEntry) {
        var analystRecommendations = analystRecommendationsDataEntry.getValue();
        return AnalystRecommendationCommand.builder()
                .tickerId(analystRecommendationsDataEntry.getKey())
                .strongBuy(analystRecommendations.latestOutperform())
                .buy(analystRecommendations.latestBuy())
                .hold(analystRecommendations.latestHold())
                .sell(analystRecommendations.latestSell())
                .strongSell(analystRecommendations.latestUnderperform())
                .build();
    }
}
