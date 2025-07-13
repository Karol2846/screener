package com.stock.screener.adapter.web.finhub.mapper;

import com.stock.screener.adapter.web.finhub.model.current_price.CurrentPriceResponse;
import com.stock.screener.application.port.command.CurrentPriceCommand;

public class FinHubMapper {

    public static CurrentPriceCommand mapToCurrentPriceCommand(String ticker, CurrentPriceResponse response) {
        return new CurrentPriceCommand(ticker, response.previousClosePrice());
    }
}
