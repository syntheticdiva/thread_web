package com.smp.thread.exception;

public class MessageServiceException extends RuntimeException {
    public MessageServiceException(String message) {
        super(message);
    }

    public MessageServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
