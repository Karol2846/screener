package com.stock.screener.application.event;

import com.stock.screener.application.event.model.DomainEvent;

public interface DomainEventHandler<T extends DomainEvent> {
    void handle(T event);

    Class<T> getEventType();
}
