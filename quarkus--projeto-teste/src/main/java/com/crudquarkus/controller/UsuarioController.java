package com.crudquarkus.controller;

import com.crudquarkus.models.request.UsuarioContractRequest;
import com.crudquarkus.models.request.UsuarioCredencialRequest;
import com.crudquarkus.models.request.UsuarioCredencialTecladoVirtualRequest;
import com.crudquarkus.models.response.ContractResponse;
import com.crudquarkus.models.response.UsuarioContractResponse;
import org.jboss.resteasy.reactive.RestResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/usuario")
public interface UsuarioController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    RestResponse<ContractResponse> cadastrarUsuario(UsuarioContractRequest userDataRequest);
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    RestResponse<UsuarioContractResponse> buscarUsuario(String identificador);

    @Path("/validar-credencial")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    RestResponse<Object> validarCredenciais(UsuarioCredencialRequest credencialRequest);

    @Path("/excluir-usuario")
    @DELETE
    RestResponse<Object> excluirUsuario (String identificador);

    @Path("/atualizar-usuario")
    @PUT
    RestResponse<Object> atualizarDadosUsuario(UsuarioContractRequest updateContractRequest);

    @Path("/validar-credencial-teclado-virtual")
    @POST
    RestResponse<Object> validarCredenciaisComTecladoVirtual(UsuarioCredencialTecladoVirtualRequest credencialTecladoVirtualRequest);


}
