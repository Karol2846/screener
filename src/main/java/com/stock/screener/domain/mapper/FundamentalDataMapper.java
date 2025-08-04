package com.stock.screener.domain.mapper;

import com.stock.screener.domain.model.FundamentalData;
import com.stock.screener.application.port.command.PriceTargetCommand;
import com.stock.screener.application.port.command.StockSummaryCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = SPRING,
        nullValuePropertyMappingStrategy = IGNORE)
public abstract class FundamentalDataMapper {

    public abstract FundamentalData from(StockSummaryCommand stockSummaryCommand);

    @Mapping(target = "id.symbol", ignore = true)
    public abstract void update(@MappingTarget FundamentalData fundamentalData, StockSummaryCommand stockSummaryCommand);

    @Mapping(target = "id.symbol", ignore = true)
    public abstract void update(@MappingTarget FundamentalData fundamentalData, PriceTargetCommand priceTargetCommand);
}
