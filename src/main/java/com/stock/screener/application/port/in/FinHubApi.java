package com.stock.screener.application.port.in;

import com.stock.screener.application.port.command.CurrentPriceCommand;

public interface FinHubApi {

    CurrentPriceCommand getCurrentPrice(String symbol);
}