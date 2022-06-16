package com.somnus.gateway;

import com.somnus.datasource.entity.UsuarioEntity;

public interface UsuarioGateway {

    void cadastrarUsuario(UsuarioEntity usuarioEntity);

    UsuarioEntity buscarUsuario(String cpf);

}
