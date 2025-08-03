package com.stock.screener.application.event.model;

public interface DomainEvent<T> {
    T payload();
}
