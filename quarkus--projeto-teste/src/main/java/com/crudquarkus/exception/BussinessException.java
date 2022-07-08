package com.crudquarkus.exception;

import javax.ws.rs.core.Response.Status;

public class BussinessException extends LayerException {

    public BussinessException(String message, String layer, Status statusCode, String localizedMessage) {
        super(message, layer, statusCode, localizedMessage);
    }
}
