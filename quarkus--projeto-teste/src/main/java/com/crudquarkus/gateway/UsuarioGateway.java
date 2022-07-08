package com.crudquarkus.gateway;

import com.crudquarkus.datasource.entity.UsuarioEntity;

public interface UsuarioGateway {

    void cadastrarUsuario(UsuarioEntity usuarioEntity);

    UsuarioEntity buscarUsuario(String cpf);

    void excluirUsuario(String identificador);
}
