package com.stock.screener.application.event.model;

import com.stock.screener.application.port.command.PriceTargetCommand;

public record PriceTargetEvent(PriceTargetCommand payload) implements DomainEvent<PriceTargetCommand> {
}
