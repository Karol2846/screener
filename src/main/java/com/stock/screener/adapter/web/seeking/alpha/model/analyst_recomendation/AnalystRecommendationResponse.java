package com.stock.screener.adapter.web.seeking.alpha.model.analyst_recomendation;

import java.util.Map;

public record AnalystRecommendationResponse(
        Map<String, Object> revisions,

        Map<String, AnalystRecommendationsData> estimates
) {}
