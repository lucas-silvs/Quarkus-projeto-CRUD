package com.crudquarkus.controller.impl;

import com.crudquarkus.controller.UsuarioController;
import com.crudquarkus.models.request.UsuarioContractRequest;
import com.crudquarkus.models.request.UsuarioCredencialRequest;
import com.crudquarkus.models.request.UsuarioCredencialTecladoVirtualRequest;
import com.crudquarkus.models.response.ContractResponse;
import com.crudquarkus.models.response.UsuarioContractResponse;
import com.crudquarkus.service.UsuarioService;
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<ContractResponse> cadastrarUsuario(UsuarioContractRequest userDataRequest) {
        service.cadastrarUsuario(userDataRequest);
        ContractResponse response = new ContractResponse("local", "sucesso", "Usuario com nome: " + userDataRequest.getNome() + " cadastrado com sucesso");
        return RestResponse.ResponseBuilder.create(Response.Status.OK, response).build();
    }

    @GET
    public RestResponse<UsuarioContractResponse> buscarUsuario(@QueryParam("identificador") String identificador) {
        UsuarioContractResponse usuario = service.buscarUsuario(identificador);
        return RestResponse.ResponseBuilder.create(Response.Status.OK, usuario).build();
    }

    @Path("/validar-credencial")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse validarCredenciais(UsuarioCredencialRequest credencialRequest) {
        service.validarCredenciais(credencialRequest);
        return RestResponse.noContent();
    }

    @Path("/excluir-usuario")
    @DELETE
    public RestResponse<Object> excluirUsuario(@QueryParam("identificador") String identificador) {
        service.excluirUsuario(identificador);
        return RestResponse.noContent();
    }

    @Path("/atualizar-usuario")
    @PUT
    public RestResponse<Object> atualizarDadosUsuario(UsuarioContractRequest updateContractRequest) {
        service.atualizadDadosUsuario(updateContractRequest);
        return RestResponse.noContent();
    }

    @Path("/validar-credencial-teclado-virtual")
    public RestResponse validarCredenciaisComTecladoVirtual(UsuarioCredencialTecladoVirtualRequest credencialTecladoVirtualRequest) {
        service.validarCredenciaisTecladoVirtual(credencialTecladoVirtualRequest);
        return RestResponse.noContent();
    }


}
