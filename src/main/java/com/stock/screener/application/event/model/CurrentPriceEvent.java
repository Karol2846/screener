package com.stock.screener.application.event.model;

import com.stock.screener.application.port.command.CurrentPriceCommand;

public record CurrentPriceEvent(CurrentPriceCommand payload) implements DomainEvent<CurrentPriceCommand> {
}
