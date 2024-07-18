package com.crudquarkus.service;

import com.crudquarkus.models.request.UsuarioContractRequest;
import com.crudquarkus.models.request.UsuarioCredencialRequest;
import com.crudquarkus.models.request.UsuarioCredencialTecladoVirtualRequest;
import com.crudquarkus.models.response.UsuarioContractResponse;

import java.util.List;

public interface UsuarioService {

 void cadastrarUsuario(UsuarioContractRequest usuarioContractRequest);

 UsuarioContractResponse buscarUsuario(String identificador);

 List<UsuarioContractResponse> listaUsuario();

 void validarCredenciais(UsuarioCredencialRequest credencialRequest);

 void excluirUsuario(String identificador);

 void atualizadDadosUsuario(UsuarioContractRequest updateContractRequest);

 void validarCredenciaisTecladoVirtual(UsuarioCredencialTecladoVirtualRequest credencialTecladoVirtualRequest);

 void validarCredenciaisTecladoVirtualBinario(UsuarioCredencialTecladoVirtualRequest credencialTecladoVirtualRequest);

 void validarCredenciaisTecladoVirtualParalelo(UsuarioCredencialTecladoVirtualRequest credencialTecladoVirtualRequest);


}
