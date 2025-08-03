package com.stock.screener.application.event.model;

import com.stock.screener.application.port.command.StockSummaryCommand;

public record StockSummaryEvent(StockSummaryCommand payload) implements DomainEvent<StockSummaryCommand> {
}
