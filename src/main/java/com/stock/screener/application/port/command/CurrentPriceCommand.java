package com.stock.screener.application.port.command;

import java.math.BigDecimal;

public record CurrentPriceCommand(String ticker, BigDecimal currentPrice) {}
