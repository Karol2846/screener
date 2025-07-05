package com.stock.screener.application.port.command;

public record AnalystRecommendationCommand(Integer tickerId,
                                           Integer strongSell,
                                           Integer sell,
                                           Integer hold,
                                           Integer buy,
                                           Integer strongBuy,
                                           Integer total
) {}