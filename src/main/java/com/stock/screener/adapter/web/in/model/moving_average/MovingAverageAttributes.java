package com.stock.screener.adapter.web.in.model.moving_average;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record MovingAverageAttributes(
        @JsonProperty("movAvg50d") BigDecimal average50Days,
        @JsonProperty("movAvg100d") BigDecimal average100Days,
        @JsonProperty("movAvg200d") BigDecimal average200Days
) {
}
