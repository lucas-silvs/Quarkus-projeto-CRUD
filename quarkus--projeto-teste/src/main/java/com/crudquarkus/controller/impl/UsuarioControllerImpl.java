package com.crudquarkus.controller.impl;


import com.crudquarkus.api.UsuarioControllerApi;
import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.model.ContractResponse;
import com.crudquarkus.service.UsuarioService;
import org.jboss.resteasy.reactive.RestResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usuario")
public class UsuarioControllerImpl implements UsuarioControllerApi {

    private final UsuarioService service;

    @Inject
    public UsuarioControllerImpl(UsuarioService service) {
        this.service = service;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String cadastrarUsuario(com.crudquarkus.model.UsuarioContractRequest userDataRequest) {
        try {
            service.cadastrarUsuario(userDataRequest);
            ContractResponse response = new ContractResponse();
            response.setOrigin("local");
            response.setSituacaoRequest("Sucesso");
            response.setMensagemParaUsuario("Usuario com nome: " + userDataRequest.getNome() + " cadastrado com sucesso");
            return RestResponse.ResponseBuilder.create(Response.Status.OK, response).build().toString();
        } catch (RuntimeException e) {
            ContractResponse response = new ContractResponse();
            response.setOrigin("local");
            response.setSituacaoRequest("error");
            response.setMensagemParaUsuario("aconteceu algum erro ao cadastrar usuario. Error: " + e);
            return RestResponse.ResponseBuilder.create(Response.Status.NOT_ACCEPTABLE, response).build().toString();
        }
    }

    @GET
    public String buscarUsuario(@QueryParam("identificador") String identificador) {
        UsuarioEntity usuario = service.buscarUsuario(identificador);
        return RestResponse.ResponseBuilder.create(Response.Status.OK, usuario).build().toString();
    }



}
