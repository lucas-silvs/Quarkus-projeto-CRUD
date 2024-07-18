package com.crudquarkus.controller;

import com.crudquarkus.models.request.UsuarioContractRequest;
import com.crudquarkus.models.request.UsuarioCredencialRequest;
import com.crudquarkus.models.request.UsuarioCredencialTecladoVirtualRequest;
import com.crudquarkus.models.response.ContractResponse;
import com.crudquarkus.models.response.UsuarioContractResponse;
import org.jboss.resteasy.reactive.RestResponse;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/usuario")
public interface UsuarioController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    RestResponse<ContractResponse> cadastrarUsuario(UsuarioContractRequest userDataRequest);
    @GET
    @Path("/{identificador}")
    @Produces(MediaType.APPLICATION_JSON)
    RestResponse<UsuarioContractResponse> buscarUsuario(@PathParam ("identificador") String identificador);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    RestResponse<List<UsuarioContractResponse>> listaUsuario();

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


    @Path("/validar-credencial-teclado-virtual/list/paralelo")
    @POST
    RestResponse<Object> validarCredenciaisComTecladoVirtualParaleloList(UsuarioCredencialTecladoVirtualRequest credencialTecladoVirtualRequest);

}
