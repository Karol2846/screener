package com.stock.screener.adapter.web.in.model.moving_average;

import java.util.List;

public record MovingAverageRequest(
        List<MovingAverage> data
) {
}
