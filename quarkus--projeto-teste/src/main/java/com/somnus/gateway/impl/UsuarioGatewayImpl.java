package com.somnus.gateway.impl;

import com.somnus.datasource.entity.UsuarioEntity;
import com.somnus.datasource.repository.UsuarioRepository;
import com.somnus.exception.GatewayException;
import com.somnus.gateway.UsuarioGateway;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@ApplicationScoped
public class UsuarioGatewayImpl implements UsuarioGateway {

    @Inject
    UsuarioRepository usuarioRepository;


    public void cadastrarUsuario(UsuarioEntity usuarioEntity) {
        if(usuarioUnicoNaBase(usuarioEntity.getCpf())){
            usuarioRepository.cadastrarUsuario(usuarioEntity);
        }
        else {
            throw new GatewayException("Usuario existente na base");
        }
    }
    private boolean usuarioUnicoNaBase(String cpf) {
        return usuarioRepository.buscarPeloCpfCnpj(cpf) == null ? true : false;
    }


    public UsuarioEntity buscarUsuario(String identificador) {
        try {
            return Optional.ofNullable(usuarioRepository.buscarPeloCpfCnpj(identificador))
                    .orElseThrow(NotFoundException::new);
        } catch (NotFoundException e) {
            throw new GatewayException("Usuario não encontrado");
        }
    }
}
