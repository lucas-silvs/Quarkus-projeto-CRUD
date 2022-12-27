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
import javax.ws.rs.core.Response;


public class UsuarioControllerImpl implements UsuarioController {

    private final UsuarioService service;

    @Inject
    public UsuarioControllerImpl(UsuarioService service) {
        this.service = service;
    }

    public RestResponse<ContractResponse> cadastrarUsuario(UsuarioContractRequest userDataRequest) {
        service.cadastrarUsuario(userDataRequest);
        ContractResponse response = new ContractResponse("local", "sucesso", "Usuario com nome: " + userDataRequest.getNome() + " cadastrado com sucesso");
        return RestResponse.ResponseBuilder.create(Response.Status.OK, response).build();
    }

    public RestResponse<UsuarioContractResponse> buscarUsuario( String identificador) {
        UsuarioContractResponse usuario = service.buscarUsuario(identificador);
        return RestResponse.ResponseBuilder.create(Response.Status.OK, usuario).build();
    }

    public RestResponse<Object> validarCredenciais(UsuarioCredencialRequest credencialRequest) {
        service.validarCredenciais(credencialRequest);
        return RestResponse.noContent();
    }

    public RestResponse<Object> excluirUsuario(String identificador) {
        service.excluirUsuario(identificador);
        return RestResponse.noContent();
    }

    public RestResponse<Object> atualizarDadosUsuario(UsuarioContractRequest updateContractRequest) {
        service.atualizadDadosUsuario(updateContractRequest);
        return RestResponse.noContent();
    }

    public RestResponse<Object> validarCredenciaisComTecladoVirtual(UsuarioCredencialTecladoVirtualRequest credencialTecladoVirtualRequest) {
        service.validarCredenciaisTecladoVirtual(credencialTecladoVirtualRequest);
        return RestResponse.noContent();
    }

}
