package com.crudquarkus.config.handler;

import com.crudquarkus.exception.LayerException;
import com.crudquarkus.models.response.ErrorResponseContract;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LayerExceptionHandler  implements ExceptionMapper<LayerException> {
    @Override
    public Response toResponse(LayerException exception) {
        ErrorResponseContract errorResponseContract = new ErrorResponseContract(
                exception.getLayer(),
                exception.getMessage(),
                exception.getLocalizedMessage()
        );
        return Response.status(exception.getStatus()).entity(errorResponseContract).build();
    }
}
