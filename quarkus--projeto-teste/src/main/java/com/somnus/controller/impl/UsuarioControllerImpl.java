package com.somnus.controller.impl;

import com.somnus.controller.UsuarioController;
import com.somnus.datasource.models.UsuarioEntity;
import com.somnus.models.request.UsuarioContractRequest;
import com.somnus.models.response.ContractResponse;
import com.somnus.service.UsuarioService;
import org.jboss.resteasy.reactive.RestResponse;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/usuario")
public class UsuarioControllerImpl implements UsuarioController {
    @Inject
    UsuarioService service;


    @POST
    @Path("/cadastrar-usuario")
    public RestResponse<ContractResponse> cadastrarUsuario(UsuarioContractRequest userDataRequest) {
        try {
            UsuarioEntity entity = service.cadastrarUsuario(userDataRequest);
            ContractResponse response = new ContractResponse("local", "sucesso", "Usuario com nome: "+entity.getNome() + "cadastrado com sucesso");
            return  RestResponse.ok(response);
        }catch (Exception e){
            ContractResponse response = new ContractResponse("local", "Error", "aconteceu algum erro ao cadastrar usuario. Error: " + e);
            return RestResponse.ResponseBuilder.create(Response.Status.NOT_ACCEPTABLE, response).build();
        }

    }
}
