package com.crudquarkus.models.response;

public class ErrorResponseContract {

    private final String layer;
    private final String message;
    private final String localizedMessage;

    public ErrorResponseContract(String layer, String message, String localizedMessage) {
        this.layer = layer;
        this.message = message;
        this.localizedMessage = localizedMessage;
    }

    public String getLayer() {
        return layer;
    }

    public String getMessage() {
        return message;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }
}
