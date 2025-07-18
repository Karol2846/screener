package com.stock.screener.application.port.command;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record StockSummaryCommand(String ticker,
                                  Integer tickerId,
                                  String companyName,
                                  String sector,
                                  Long marketCap,
                                  Long enterpriseValue,
                                  BigDecimal peRatio,
                                  BigDecimal pbRatio,
                                  BigDecimal peForward,
                                  BigDecimal evEbitda,
                                  BigDecimal evSales,
                                  BigDecimal eps,
                                  BigDecimal forwardEps3Y
) { }
