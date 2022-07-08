package com.crudquarkus.exception;

import javax.ws.rs.core.Response.Status;

public class LayerException extends RuntimeException{

    private final String layer;
    private final Status status;
    private final String localizedMessage;

    public LayerException(String message, String layer, Status statusCode, String localizedMessage) {
        super(message);
        this.layer = layer;
        this.status = statusCode;
        this.localizedMessage = localizedMessage;
    }

    public String getLayer() {
        return layer;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String getLocalizedMessage() {
        return localizedMessage;
    }
}
