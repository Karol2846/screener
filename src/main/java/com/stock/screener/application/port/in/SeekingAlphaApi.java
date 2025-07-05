package com.stock.screener.application.port.in;

import com.stock.screener.application.port.command.AnalystRecommendationCommand;
import com.stock.screener.application.port.command.MovingAveragesCommand;
import com.stock.screener.application.port.command.PriceTargetCommand;
import com.stock.screener.application.port.command.StockSummaryCommand;

import java.util.List;

public interface SeekingAlphaApi {

    List<StockSummaryCommand> getStockSummary(String symbols);

    List<MovingAveragesCommand> getMovingAverages(String symbols);

    List<PriceTargetCommand> getPriceTarget(String tickerIds);

    List<AnalystRecommendationCommand> getAnalystRecommendation(String tickerIds);
}
