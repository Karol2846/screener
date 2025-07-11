package com.stock.screener.application.port.in.api;

import com.stock.screener.application.port.command.CurrentPriceCommand;

public interface FinHubApi {
    //TODO: implement this API
    CurrentPriceCommand getCurrentPrice(String symbol);
}