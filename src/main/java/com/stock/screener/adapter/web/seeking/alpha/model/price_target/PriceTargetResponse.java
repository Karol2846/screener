package com.stock.screener.adapter.web.seeking.alpha.model.price_target;

import java.util.Map;

public record PriceTargetResponse(
        Map<String, Object> revisions,

        Map<String, PriceTargetData> estimates
) {}