package com.stock.screener.adapter.web.seeking.alpha.model.price_target;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.screener.adapter.web.seeking.alpha.model.TimeSeriesData;

public record PriceTargetData(
        @JsonProperty("target_price_low")
        TimeSeriesData targetPriceLow,

        @JsonProperty("target_price_high")
        TimeSeriesData targetPriceHigh,

        @JsonProperty("target_price")
        TimeSeriesData targetPrice
) {
    public Double getLatestLowPrice() {
        return targetPriceLow != null ? targetPriceLow.getLatest().getValueAsDouble() : null;
    }

    public Double getLatestHighPrice() {
        return targetPriceHigh != null ? targetPriceHigh.getLatest().getValueAsDouble() : null;
    }

    public Double getLatestTargetPrice() {
        return targetPrice != null ? targetPrice.getLatest().getValueAsDouble() : null;
    }
}
