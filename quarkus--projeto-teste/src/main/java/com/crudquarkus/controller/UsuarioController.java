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
    RestResponse<UsuarioContractResponse> buscarUsuario(@QueryParam("identificador") String identificador);

    @Path("/validar-credencial")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    RestResponse<Object> validarCredenciais(UsuarioCredencialRequest credencialRequest);

    @Path("/excluir-usuario")
    @DELETE
    RestResponse<Object> excluirUsuario (@QueryParam("identificador") String identificador);

    @Path("/atualizar-usuario")
    @PUT
    RestResponse<Object> atualizarDadosUsuario(UsuarioContractRequest updateContractRequest);

    @Path("/validar-credencial-teclado-virtual")
    @POST
    RestResponse<Object> validarCredenciaisComTecladoVirtual(UsuarioCredencialTecladoVirtualRequest credencialTecladoVirtualRequest);

    @Path("/validar-credencial-teclado-virtual/binario")
    @POST
    RestResponse<Object> validarCredenciaisComTecladoVirtualBinario(UsuarioCredencialTecladoVirtualRequest credencialTecladoVirtualRequest);

    @Path("/validar-credencial-teclado-virtual/paralelo")
    @POST
    RestResponse<Object> validarCredenciaisComTecladoVirtualParalelo(UsuarioCredencialTecladoVirtualRequest credencialTecladoVirtualRequest);

}
