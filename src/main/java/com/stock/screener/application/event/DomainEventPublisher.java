package com.stock.screener.application.event;

import com.stock.screener.application.event.model.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
