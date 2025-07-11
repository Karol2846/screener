package com.stock.screener.adapter.web.seeking.alpha.model.price_target;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.screener.adapter.web.seeking.alpha.model.TimeSeriesData;

import java.math.BigDecimal;

public record PriceTargetData(
        @JsonProperty("target_price_low")
        TimeSeriesData targetPriceLow,

        @JsonProperty("target_price_high")
        TimeSeriesData targetPriceHigh,

        @JsonProperty("target_price")
        TimeSeriesData targetPrice
) {
    //TODO: json annotation on methods only reading purpose, later you can delete them
    @JsonProperty("latestLowPrice")
    public BigDecimal latestLowPrice() {
        return targetPriceLow != null ? targetPriceLow.getLatest().dataItemValue() : null;
    }

    @JsonProperty("latestHighPrice")
    public BigDecimal latestHighPrice() {
        return targetPriceHigh != null ? targetPriceHigh.getLatest().dataItemValue() : null;
    }

    @JsonProperty("latestTargetPrice")
    public BigDecimal latestTargetPrice() {
        return targetPrice != null ? targetPrice.getLatest().dataItemValue() : null;
    }
}
