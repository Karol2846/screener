package com.stock.screener.adapter.web.seeking.alpha.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record DataPoint(
        @JsonProperty("effectivedate") String effectiveDate,
        @JsonProperty("dataitemvalue") BigDecimal dataItemValue
) {}
