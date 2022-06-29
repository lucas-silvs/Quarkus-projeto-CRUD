package com.crudquarkus.controller;

import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.models.request.UsuarioContractRequest;
import com.crudquarkus.models.response.ContractResponse;
import org.jboss.resteasy.reactive.RestResponse;

public interface UsuarioController {

    RestResponse<ContractResponse> cadastrarUsuario(UsuarioContractRequest userDataRequest);

    RestResponse<UsuarioEntity> buscarUsuario(String identificador);




}
