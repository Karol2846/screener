package com.stock.screener.adapter.web.seeking.alpha.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DataPoint(
        @JsonProperty("effectivedate") String effectiveDate,
        @JsonProperty("dataitemvalue") String dataItemValue
) {
    public Double getValueAsDouble() {
        return dataItemValue != null ? Double.parseDouble(dataItemValue) : null;
    }

    public Integer getValueAsInt() {
        return dataItemValue != null ? Integer.parseInt(dataItemValue) : null;
    }
}
