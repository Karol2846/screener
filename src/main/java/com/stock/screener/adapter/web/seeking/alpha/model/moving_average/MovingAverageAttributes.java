package com.stock.screener.adapter.web.seeking.alpha.model.moving_average;

import java.math.BigDecimal;

public record MovingAverageAttributes(BigDecimal movAvg50d,
                                      BigDecimal movAvg100d,
                                      BigDecimal movAvg200d
) {
}
