package com.stock.screener.domain.mapper;

import com.stock.screener.domain.model.CompoundId;
import com.stock.screener.domain.model.FundamentalData;
import com.stock.screener.domain.model.Stock;
import com.stock.screener.application.port.command.PriceTargetCommand;
import com.stock.screener.application.port.command.StockSummaryCommand;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = SPRING,
        nullValuePropertyMappingStrategy = IGNORE,
        injectionStrategy = CONSTRUCTOR)
public abstract class FundamentalDataMapper {
    //TODO: to podejściie jest złe, ale chat wpadł na lepsze:
    //    https://chatgpt.com/c/688f8632-5114-8329-af1d-b2eae274b471
    @Autowired
    protected StockManager stockManager;

    public abstract FundamentalData from(StockSummaryCommand stockSummaryCommand);

    @Mapping(target = "id.symbol", ignore = true)
    public abstract void update(@MappingTarget FundamentalData fundamentalData, StockSummaryCommand stockSummaryCommand);

    @Mapping(target = "id.symbol", ignore = true)
    public abstract void update(@MappingTarget FundamentalData fundamentalData, PriceTargetCommand priceTargetCommand);

    @AfterMapping
    void calculateAdditionalFields(@MappingTarget FundamentalData fundamentalData, StockSummaryCommand stockSummaryCommand) {
        fundamentalData.setStock(getStock(stockSummaryCommand.ticker()));
        fundamentalData.setId(CompoundId.forSymbolWithActualDate(stockSummaryCommand.ticker()));

        fundamentalData.calculateEbitda();
        fundamentalData.calculateRevenue();
        fundamentalData.calculatePegForward();
    }

    protected Stock getStock(String ticker) {
        return stockManager.getReference(ticker);
    }
}
