package com.stock.screener.application.port.command;

import java.math.BigDecimal;

public record PriceTargetCommand(Integer tickerId,
                                 BigDecimal priceTargetHigh,
                                 BigDecimal priceTargetLow,
                                 BigDecimal priceTargetConsensus
) {}
