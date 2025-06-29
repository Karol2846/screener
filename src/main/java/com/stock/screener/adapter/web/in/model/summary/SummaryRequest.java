package com.stock.screener.adapter.web.in.model.summary;

import java.util.List;

public record SummaryRequest(
        List<Summary> data
) {
}
