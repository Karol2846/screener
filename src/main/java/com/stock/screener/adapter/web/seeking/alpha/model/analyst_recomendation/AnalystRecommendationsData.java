package com.stock.screener.adapter.web.seeking.alpha.model.analyst_recomendation;

import com.stock.screener.adapter.web.seeking.alpha.model.TimeSeriesData;

public record AnalystRecommendationsData(
        TimeSeriesData underperform,
        TimeSeriesData outperform,
        TimeSeriesData hold,
        TimeSeriesData sell,
        TimeSeriesData buy
) {}
