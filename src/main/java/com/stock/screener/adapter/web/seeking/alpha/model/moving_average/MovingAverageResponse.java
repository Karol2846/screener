package com.stock.screener.adapter.web.seeking.alpha.model.moving_average;

import java.util.List;

public record MovingAverageResponse(
        List<MovingAverage> data
) {
}
