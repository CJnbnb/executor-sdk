package com.executor.sdk.exception;

public class ExecutorSdkException extends RuntimeException {

    public ExecutorSdkException(String message) {
        super(message);
    }

    public ExecutorSdkException(String message, Throwable cause) {
        super(message, cause);
    }
}
