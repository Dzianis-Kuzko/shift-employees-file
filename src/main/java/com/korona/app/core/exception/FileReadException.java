package com.korona.app.core.exception;

public class FileReadException extends RuntimeException {
    public FileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
