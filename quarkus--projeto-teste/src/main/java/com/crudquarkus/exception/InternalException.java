package com.crudquarkus.exception;

public class InternalException extends RuntimeException{

    private final String localizedMessage;

    public InternalException(String message) {
        super(message);
        this.localizedMessage = null;
    }

    public InternalException(String message, String localizedMessage) {
        super(message);
        this.localizedMessage = localizedMessage;

    }


}
