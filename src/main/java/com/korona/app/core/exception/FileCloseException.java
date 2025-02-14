package com.korona.app.core.exception;

public class FileCloseException extends RuntimeException {
    public FileCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
