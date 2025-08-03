package com.stock.screener.application.event;

import com.stock.screener.application.event.model.DomainEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

public interface DomainEventHandler<T extends DomainEvent<?>> {

    @Async
    @EventListener
    @Transactional
    void handle(T event);
}
