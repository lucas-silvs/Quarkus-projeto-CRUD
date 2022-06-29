package com.crudquarkus.gateway.impl;

import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.datasource.repository.UsuarioRepository;
import com.crudquarkus.exception.GatewayException;
import com.crudquarkus.gateway.UsuarioGateway;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@ApplicationScoped
public class UsuarioGatewayImpl implements UsuarioGateway {


    UsuarioRepository usuarioRepository;

    @Inject
    public UsuarioGatewayImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void cadastrarUsuario(UsuarioEntity usuarioEntity) {
        if(usuarioUnicoNaBase(usuarioEntity.getCpf())){
            usuarioRepository.cadastrarUsuario(usuarioEntity);
        }
        else {
            throw new GatewayException("Usuario existente na base");
        }
    }
    private boolean usuarioUnicoNaBase(String cpf) {
        return usuarioRepository.buscarPeloCpfCnpj(cpf) == null;
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
