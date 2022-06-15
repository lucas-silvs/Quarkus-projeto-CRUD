package com.somnus.service.impl;

import com.somnus.datasource.models.UsuarioEntity;
import com.somnus.datasource.repository.UsuarioRepository;
import com.somnus.models.request.UsuarioContractRequest;
import com.somnus.service.UsuarioService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    public UsuarioEntity cadastrarUsuario(UsuarioContractRequest usuarioContractRequest) {
        UsuarioEntity novoUsuario = usuarioContractRequest.toUsuarioEntity();
        usuarioRepository.cadastrarUsuario(novoUsuario);
        return novoUsuario;
    }
}
