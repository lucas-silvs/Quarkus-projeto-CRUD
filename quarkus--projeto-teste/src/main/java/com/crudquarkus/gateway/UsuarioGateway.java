package com.crudquarkus.gateway;

import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.models.request.UsuarioContractRequest;

public interface UsuarioGateway {

    void cadastrarUsuario(UsuarioEntity usuarioEntity);

    UsuarioEntity buscarUsuario(String cpf);

    void excluirUsuario(String identificador);

    void atualizarDadosUsuario(UsuarioContractRequest usuarioEntity);
}
