package com.stock.screener.adapter.web.seeking.alpha.model.summary;

public record Summary(
        String id,
        int tickerId,
        SummaryAttributes attributes
) {
}
