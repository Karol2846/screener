package com.stock.screener.adapter.web.seeking.alpha.model.moving_average;

public record MovingAverage(
        String id,
        int tickerId,
        MovingAverageAttributes attributes
) {
}
