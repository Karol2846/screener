package com.stock.screener.adapter.web.seeking.alpha.model.current_price;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CurrentPriceRequest(
        @JsonProperty("c") BigDecimal currentPrice,
        @JsonProperty("d") BigDecimal change,
        @JsonProperty("dp") BigDecimal percentChange,
        @JsonProperty("h") BigDecimal dayHighPrice,
        @JsonProperty("l") BigDecimal dayLowPrice,
        @JsonProperty("o") BigDecimal openPrice,
        @JsonProperty("pc") BigDecimal previousCloseProce,
        @JsonProperty("t") BigDecimal t
) {}
