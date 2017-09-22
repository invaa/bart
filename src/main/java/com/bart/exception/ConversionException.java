package com.bart.exception;

public class ConversionException extends RuntimeException {
    public ConversionException(final String message) {
        super(message);
    }
    public ConversionException(final String message, final CartException ex) {
        super(message, ex);
    }
}
