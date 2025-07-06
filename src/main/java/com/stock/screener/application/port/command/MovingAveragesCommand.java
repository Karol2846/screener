package com.stock.screener.application.port.command;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MovingAveragesCommand(String ticker,
                                    BigDecimal average50Days,
                                    BigDecimal average100Days,
                                    BigDecimal average200Days
) {}
