package com.crudquarkus.exception;

import javax.ws.rs.core.Response.Status;

public class GatewayException extends LayerException{

    public GatewayException(String message, String layer, Status statusCode, String localizedMessage) {
        super(message, layer, statusCode, localizedMessage);
    }

}
