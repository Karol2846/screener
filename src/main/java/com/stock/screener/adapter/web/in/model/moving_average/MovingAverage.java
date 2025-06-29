package com.stock.screener.adapter.web.in.model.moving_average;

public record MovingAverage(
        String id,
        int tickerId,
        MovingAverageAttributes attributes
) {
}
