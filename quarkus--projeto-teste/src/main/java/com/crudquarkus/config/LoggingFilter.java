package com.crudquarkus.config;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.JBossLogFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Provider
public class LoggingFilter implements ContainerResponseFilter {

    private static final Log LOG = JBossLogFactory.getFactory().getInstance(LoggingFilter.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Context
    UriInfo info;

    @Context
    HttpServerRequest request;

    @Context
    HttpServerResponse response;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        final String method = requestContext.getMethod();
        // TODO implementar captura do Request Body para implementação de Log manual
        final String body = requestContext.getEntityStream().toString();
        final String path = info.getRequestUri().toString();
        final String address = request.remoteAddress().toString();
        final String timeStamp = formatter.format(LocalDateTime.now());

        LOG.info(String.format("%s -- Request: %s - Path: %s -- Body: %s", timeStamp, method, path, body));



    }
}
