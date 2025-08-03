package com.stock.screener.application.event.model;

import com.stock.screener.application.port.command.MovingAveragesCommand;

public record MovingAveragesEvent(MovingAveragesCommand payload) implements DomainEvent<MovingAveragesCommand> {
}
