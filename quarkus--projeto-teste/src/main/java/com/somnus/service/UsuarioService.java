package com.somnus.service;

import com.somnus.datasource.entity.UsuarioEntity;
import com.somnus.models.request.UsuarioContractRequest;

public interface UsuarioService {

 void cadastrarUsuario(UsuarioContractRequest usuarioContractRequest);

 UsuarioEntity buscarUsuario(String identificador);


}
