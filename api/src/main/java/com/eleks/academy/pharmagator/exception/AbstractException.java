package com.eleks.academy.pharmagator.exception;

public abstract class AbstractException extends RuntimeException {

    public AbstractException(String message) {
        super(message);
    }

    public AbstractException(String message, Throwable cause) {
        super(message, cause);
    }
}
