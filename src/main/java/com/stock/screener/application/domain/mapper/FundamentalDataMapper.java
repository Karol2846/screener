package com.stock.screener.application.domain.mapper;

import com.stock.screener.application.domain.model.CompoundId;
import com.stock.screener.application.domain.model.FundamentalData;
import com.stock.screener.application.domain.model.Stock;
import com.stock.screener.application.port.command.PriceTargetCommand;
import com.stock.screener.application.port.command.StockSummaryCommand;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(nullValuePropertyMappingStrategy = IGNORE)
public interface FundamentalDataMapper {

    default FundamentalData fromStock(Stock  stock) {
        return FundamentalData.builder()
                .id(CompoundId.builder()
                        .symbol(stock.getSymbol())
                        .build())
                .build();
    }

    @Mapping(target = "id.symbol", ignore = true)
    void update(@MappingTarget FundamentalData fundamentalData, StockSummaryCommand stockSummaryCommand);

    @Mapping(target = "id.symbol", ignore = true)
    void update(@MappingTarget FundamentalData fundamentalData, PriceTargetCommand priceTargetCommand);

    @AfterMapping
    default void calculateAdditionalFields(@MappingTarget FundamentalData fundamentalData) {
        fundamentalData.calculateEbitda();
        fundamentalData.calculateRevenue();
        fundamentalData.calculatePegForward();
    }


    @Mapping(target = "id.createdAt", ignore = true)
    void update(@MappingTarget FundamentalData existingData, FundamentalData updatedFundamentalData);
}
