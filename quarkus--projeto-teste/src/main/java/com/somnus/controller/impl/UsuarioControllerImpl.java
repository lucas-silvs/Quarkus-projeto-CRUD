package com.somnus.controller.impl;

import com.somnus.controller.UsuarioController;
import com.somnus.datasource.entity.UsuarioEntity;
import com.somnus.models.request.UsuarioContractRequest;
import com.somnus.models.response.ContractResponse;
import com.somnus.service.UsuarioService;
import org.jboss.resteasy.reactive.RestResponse;

import javax.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usuario")
public class UsuarioControllerImpl implements UsuarioController {

    private final UsuarioService service;

    @Inject
    public UsuarioControllerImpl(UsuarioService service) {
        this.service = service;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<ContractResponse> cadastrarUsuario(UsuarioContractRequest userDataRequest) {
        try {
            service.cadastrarUsuario(userDataRequest);
            ContractResponse response = new ContractResponse("local", "sucesso", "Usuario com nome: " + userDataRequest.getNome() + " cadastrado com sucesso");
            return RestResponse.ResponseBuilder.create(Response.Status.OK, response).build();
        } catch (Exception e) {
            ContractResponse response = new ContractResponse("local", "error", "aconteceu algum erro ao cadastrar usuario. Error: " + e);
            return RestResponse.ResponseBuilder.create(Response.Status.NOT_ACCEPTABLE, response).build();
        }
    }

    @GET
    public RestResponse<UsuarioEntity> buscarUsuario(@QueryParam("identificador") String identificador) {
        UsuarioEntity usuario = service.buscarUsuario(identificador);
        return RestResponse.ResponseBuilder.create(Response.Status.OK, usuario).build();
    }

}
