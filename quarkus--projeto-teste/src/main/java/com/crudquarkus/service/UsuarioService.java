package com.crudquarkus.service;

import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.model.UsuarioContractRequest;


public interface UsuarioService {

 void cadastrarUsuario(UsuarioContractRequest usuarioContractRequest);

 UsuarioEntity buscarUsuario(String identificador);


}
