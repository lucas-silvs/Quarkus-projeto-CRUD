package com.crudquarkus.gateway.impl;

import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.datasource.repository.UsuarioRepository;
import com.crudquarkus.exception.LayerException;
import com.crudquarkus.gateway.UsuarioGateway;
import com.crudquarkus.models.request.UsuarioContractRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioGatewayImpl implements UsuarioGateway {


    public static final String GATEWAY = "GATEWAY";
    UsuarioRepository usuarioRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioGatewayImpl.class);

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
            LOGGER.info("Buscando usuario por CPF: {}", identificador);

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

    @Transactional
    public void atualizarDadosUsuario(UsuarioContractRequest usuarioContractRequest) {
        UsuarioEntity entity = usuarioRepository.buscarPeloCpfCnpj(usuarioContractRequest.getCpf());
        mapearAlteracoesUsuarioEntity(entity, usuarioContractRequest);
        usuarioRepository.persist(entity);

    }

    @Override
    public UsuarioEntity buscarUsuarioPorEmail(String identificador) {
        try {
                LOGGER.info("Buscando usuario por email: {}", identificador);
                return Optional.ofNullable(usuarioRepository.buscaPeloEmail(identificador))
                    .orElseThrow(NotFoundException::new);
        } catch (NotFoundException e) {
            throw new LayerException("Usuario não encontrado", GATEWAY, Status.NOT_FOUND, "UsuarioGatewayImpl.buscarUsuario()");
        }    }

    @Override
    public List<UsuarioEntity> listarUsuarios() {
        return usuarioRepository.listAll();
    }


    private void mapearAlteracoesUsuarioEntity(UsuarioEntity usuarioEntity, UsuarioContractRequest updateContractRequest) {
        usuarioEntity.setEmail(updateContractRequest.getEmail());
        usuarioEntity.setNome(updateContractRequest.getNome());
        usuarioEntity.setTelefone(updateContractRequest.getTelefone());
        usuarioEntity.setLogin(updateContractRequest.getLogin());
    }
}
