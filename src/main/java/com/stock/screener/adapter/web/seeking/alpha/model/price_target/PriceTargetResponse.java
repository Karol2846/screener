package com.stock.screener.adapter.web.seeking.alpha.model.price_target;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stock.screener.adapter.web.seeking.alpha.model.SingleTickerDeserializer;

import java.util.Map;

public record PriceTargetResponse(
        Map<String, Object> revisions,

        @JsonDeserialize(using = SingleTickerDeserializer.class)
        PriceTargetData estimates
) {}