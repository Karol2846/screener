package com.stock.screener.adapter.web.finhub.model.current_price;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CurrentPriceResponse(
        @JsonProperty("c") BigDecimal currentPrice,
        @JsonProperty("d") BigDecimal change,
        @JsonProperty("dp") BigDecimal percentChange,
        @JsonProperty("h") BigDecimal dayHighPrice,
        @JsonProperty("l") BigDecimal dayLowPrice,
        @JsonProperty("o") BigDecimal openPrice,
        @JsonProperty("pc") BigDecimal previousClosePrice,
        @JsonProperty("t") BigDecimal t
) {}
