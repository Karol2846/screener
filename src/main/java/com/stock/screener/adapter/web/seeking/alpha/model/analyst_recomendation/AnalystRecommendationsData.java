package com.stock.screener.adapter.web.seeking.alpha.model.analyst_recomendation;

import com.stock.screener.adapter.web.seeking.alpha.model.TimeSeriesData;

public record AnalystRecommendationsData(
        TimeSeriesData underperform,
        TimeSeriesData outperform,
        TimeSeriesData hold,
        TimeSeriesData sell,
        TimeSeriesData buy
) {
    public Integer latestUnderperform() {
        return underperform != null ? underperform.getLatest().dataItemValue().intValue() : null;
    }

    public Integer latestOutperform() {
        return outperform != null ? outperform.getLatest().dataItemValue().intValue() : null;
    }

    public Integer latestHold() {
        return hold != null ? hold.getLatest().dataItemValue().intValue() : null;
    }

    public Integer latestSell() {
        return sell != null ? sell.getLatest().dataItemValue().intValue() : null;
    }

    public Integer latestBuy() {
        return buy != null ? buy.getLatest().dataItemValue().intValue() : null;
    }

    public Integer latestTotal() {
        return latestUnderperform() + latestOutperform() + latestHold() + latestSell() + latestBuy();
    }
}
