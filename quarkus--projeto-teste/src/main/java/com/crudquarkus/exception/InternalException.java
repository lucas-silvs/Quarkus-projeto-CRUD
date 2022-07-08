package com.crudquarkus.exception;

import javax.ws.rs.core.Response.Status;

public class InternalException extends LayerException{

    public InternalException(String message, String layer, Status statusCode, String localizedMessage) {
        super(message, layer, statusCode, localizedMessage);
    }
}
