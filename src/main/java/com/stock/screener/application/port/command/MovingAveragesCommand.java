package com.stock.screener.application.port.command;

import java.math.BigDecimal;

public record MovingAveragesCommand(String ticker,
                                    BigDecimal average50Days,
                                    BigDecimal average100Days,
                                    BigDecimal average200Days
) {}
