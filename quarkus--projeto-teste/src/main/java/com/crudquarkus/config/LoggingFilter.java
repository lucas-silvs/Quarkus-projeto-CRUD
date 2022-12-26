package com.crudquarkus.config;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.JBossLogFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter{

    private static final Log LOG = JBossLogFactory.getFactory().getInstance(LoggingFilter.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Context
    UriInfo info;

    @Context
    HttpServerRequest request;

    @Context
    HttpServerResponse response;


    public void filter(ContainerRequestContext requestContext) throws IOException {
        final String method = requestContext.getMethod();
        final String path = info.getRequestUri().toString();
        final String timeStamp = formatter.format(LocalDateTime.now());

        String bodyOrParams = "";
        if(isJson(requestContext)) {
            InputStream inputStream = requestContext.getEntityStream();
            //TODO melhorar implementação de captura do body
            bodyOrParams = IOUtils.toString(inputStream, String.valueOf(StandardCharsets.UTF_8));
            requestContext.setEntityStream(IOUtils.toInputStream(bodyOrParams, StandardCharsets.UTF_8));
            LOG.info(String.format("%s -- Request: %s - Path: %s -- Request Body: %s", timeStamp, method, path, bodyOrParams ));
        }
        else{
            bodyOrParams = String.valueOf(request.query());
            LOG.info(String.format("%s -- Request: %s - Path: %s -- Request Param: %s", timeStamp, method, path, bodyOrParams ));
        }

    }

    private boolean isJson(ContainerRequestContext requestContext) {
        try {
            return requestContext.getMediaType().toString().contains("application/json");
        }catch (Exception e){
            LOG.info(e.getMessage());
        }
        return false;
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

    }
}
