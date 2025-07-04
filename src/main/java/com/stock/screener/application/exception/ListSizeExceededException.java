package com.stock.screener.application.exception;

//TODO: create validator that will check permitted size of the list (stored in properties)
public class ListSizeExceededException extends RuntimeException {

    public ListSizeExceededException() {
    }

    public ListSizeExceededException(String message) {
        super(message);
    }

    public ListSizeExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
