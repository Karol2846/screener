package com.stock.screener.adapter.web.in.model.summary;

public record Summary(
        String id,
        String tickerId,
        SummaryAttributes attributes
) {
}
