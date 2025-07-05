package com.stock.screener.application.port.command;

import java.math.BigDecimal;

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

                                  // TODO: maybe add those fields to FundamentalData?
                                  BigDecimal percentChange3Months,
                                  BigDecimal percentChange6Months,
                                  BigDecimal percentChange9Months,
                                  BigDecimal percentChange12Months
) {}
