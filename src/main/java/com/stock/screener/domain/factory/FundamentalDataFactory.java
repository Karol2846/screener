package com.stock.screener.domain.factory;

import com.stock.screener.application.port.command.PriceTargetCommand;
import com.stock.screener.application.port.command.StockSummaryCommand;
import com.stock.screener.domain.mapper.FundamentalDataMapper;
import com.stock.screener.domain.model.CompoundId;
import com.stock.screener.domain.model.FundamentalData;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class FundamentalDataFactory extends AbstractFactory {

    private final FundamentalDataMapper mapper;

    public FundamentalDataFactory(EntityManager entityManager, FundamentalDataMapper mapper) {
        super(entityManager);
        this.mapper = mapper;
    }

    public FundamentalData from(StockSummaryCommand command) {
        FundamentalData fundamentalData = mapper.from(command);

        fundamentalData.setStock(getReference(command.ticker()));
        fundamentalData.setId(CompoundId.forSymbolWithActualDate(command.ticker()));
        enrichFundamentalData(fundamentalData);

        return fundamentalData;
    }

    public FundamentalData from(PriceTargetCommand command, String ticker) {
        FundamentalData fundamentalData = mapper.from(command);

        fundamentalData.setStock(getReference(ticker));
        fundamentalData.setId(CompoundId.forSymbolWithActualDate(ticker));

        return fundamentalData;
    }

    public void update(FundamentalData fundamentalData, StockSummaryCommand command) {
        mapper.update(fundamentalData, command);
        enrichFundamentalData(fundamentalData);
    }

    public void update(FundamentalData fundamentalData, PriceTargetCommand command) {
        mapper.update(fundamentalData, command);
    }

    private static void enrichFundamentalData(FundamentalData fundamentalData) {
        fundamentalData.calculateEbitda();
        fundamentalData.calculateRevenue();
        fundamentalData.calculatePegForward();
    }
}
