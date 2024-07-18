package com.crudquarkus.gateway;

import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.models.request.UsuarioContractRequest;

import java.util.List;

public interface UsuarioGateway {

    void cadastrarUsuario(UsuarioEntity usuarioEntity);

    UsuarioEntity buscarUsuario(String cpf);

    void excluirUsuario(String identificador);

    void atualizarDadosUsuario(UsuarioContractRequest usuarioEntity);

    UsuarioEntity buscarUsuarioPorEmail(String identificador);

    List<UsuarioEntity> listarUsuarios();
}
