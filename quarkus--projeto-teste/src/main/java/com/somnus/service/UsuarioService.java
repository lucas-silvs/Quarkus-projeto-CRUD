package com.somnus.service;

import com.somnus.datasource.models.UsuarioEntity;
import com.somnus.models.request.UsuarioContractRequest;

public interface UsuarioService {

 UsuarioEntity cadastrarUsuario(UsuarioContractRequest usuarioContractRequest);


}
