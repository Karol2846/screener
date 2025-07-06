package com.stock.screener.application.port.command;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PriceTargetCommand(Integer tickerId,
                                 BigDecimal priceTargetHigh,
                                 BigDecimal priceTargetLow,
                                 BigDecimal priceTargetConsensus
) {}
