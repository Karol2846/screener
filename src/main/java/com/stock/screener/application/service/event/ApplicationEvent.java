package com.stock.screener.application.service.event;

public record ApplicationEvent<T>(T payload) {
    public static <T> ApplicationEvent<T> of(T payload) {
        return new ApplicationEvent<>(payload);
    }
}
