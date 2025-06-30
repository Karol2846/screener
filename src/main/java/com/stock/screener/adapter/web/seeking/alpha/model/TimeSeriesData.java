package com.stock.screener.adapter.web.seeking.alpha.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TimeSeriesData(
        @JsonProperty("0") List<DataPoint> data
) {
    public List<DataPoint> getData() {
        return data != null ? data : List.of();
    }

    public DataPoint getLatest() {
        return data != null && !data.isEmpty() ? data.getFirst() : null;
    }
}