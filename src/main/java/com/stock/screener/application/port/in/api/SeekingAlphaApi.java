package com.stock.screener.application.port.in.api;

import com.stock.screener.application.port.command.AnalystRecommendationCommand;
import com.stock.screener.application.port.command.MovingAveragesCommand;
import com.stock.screener.application.port.command.PriceTargetCommand;
import com.stock.screener.application.port.command.StockSummaryCommand;

import java.util.List;

public interface SeekingAlphaApi {

    List<StockSummaryCommand> getStockSummary(List<String> symbols);

    List<MovingAveragesCommand> getMovingAverages(List<String> symbols);

    List<PriceTargetCommand> getPriceTarget(List<String> tickerIds);

    List<AnalystRecommendationCommand> getAnalystRecommendation(List<String> tickerIds);
}
