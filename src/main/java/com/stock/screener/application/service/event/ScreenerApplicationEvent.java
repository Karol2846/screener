package com.stock.screener.application.service.event;

public record ScreenerApplicationEvent<T>(T payload) {
    public static <T> ScreenerApplicationEvent<T> of(T payload) {
        return new ScreenerApplicationEvent<>(payload);
    }
}
