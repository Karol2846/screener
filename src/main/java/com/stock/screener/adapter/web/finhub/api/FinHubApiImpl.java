package com.stock.screener.adapter.web.finhub.api;

import com.stock.screener.adapter.web.finhub.client.FinHubClient;
import com.stock.screener.application.port.command.CurrentPriceCommand;
import com.stock.screener.application.port.in.api.FinHubApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.stock.screener.adapter.web.finhub.mapper.FinHubMapper.mapToCurrentPriceCommand;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinHubApiImpl implements FinHubApi {

    private final FinHubClient client;

    @Override
    public CurrentPriceCommand getCurrentPrice(String symbol) {
        return mapToCurrentPriceCommand(symbol,client.getCurrentPrice(symbol));
    }
}
