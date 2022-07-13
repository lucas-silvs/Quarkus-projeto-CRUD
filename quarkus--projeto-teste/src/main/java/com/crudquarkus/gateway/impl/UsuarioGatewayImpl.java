package com.crudquarkus.gateway.impl;

import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.datasource.repository.UsuarioRepository;
import com.crudquarkus.exception.LayerException;
import com.crudquarkus.gateway.UsuarioGateway;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response.Status;
import java.util.Optional;

@ApplicationScoped
public class UsuarioGatewayImpl implements UsuarioGateway {


    public static final String GATEWAY = "GATEWAY";
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
            throw new LayerException("Usuario existente na base", GATEWAY, Status.CONFLICT, "UsuarioGatewayImpl.cadastrarUsuario()");
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
            throw new LayerException("Usuario não encontrado", GATEWAY, Status.NOT_FOUND, "UsuarioGatewayImpl.buscarUsuario()");
        }
    }

    @Transactional
    public void excluirUsuario(String identificador) {
        UsuarioEntity entity = buscarUsuario(identificador);
        usuarioRepository.delete(entity);
    }
}
