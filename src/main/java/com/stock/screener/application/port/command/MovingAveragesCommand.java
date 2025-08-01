package com.stock.screener.application.port.command;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MovingAveragesCommand(String ticker,
                                    BigDecimal average50Days,
                                    BigDecimal average100Days,
                                    BigDecimal average200Days
) {
    public String movingAverages() {
        return  "average50Days=" + average50Days +
                ", average100Days=" + average100Days +
                ", average200Days=" + average200Days;
    }
}
