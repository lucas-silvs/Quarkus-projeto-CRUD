package com.crudquarkus.service.impl;

import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.exception.BussinessException;
import com.crudquarkus.gateway.UsuarioGateway;
import com.crudquarkus.model.UsuarioContractRequest;
import com.crudquarkus.service.UsuarioService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    private final Validator validator;
    private final UsuarioGateway usuarioGateway;

    @Inject
    public UsuarioServiceImpl(Validator validator, UsuarioGateway usuarioGateway) {
        this.validator = validator;
        this.usuarioGateway = usuarioGateway;
    }

    public void cadastrarUsuario(UsuarioContractRequest usuarioContractRequest) {
        Set<ConstraintViolation<UsuarioContractRequest>> violations = validator.validate(usuarioContractRequest);
        if(violations.isEmpty()) {
            usuarioGateway.cadastrarUsuario(UsuarioContractConverter.toUsuarioEntity(usuarioContractRequest));
        }
        else {
            String message = ((ConstraintViolation) violations.toArray()[0]).getMessageTemplate();
            throw new BussinessException(message);
        }
    }

    @Override
    public UsuarioEntity buscarUsuario(String identificador) {
            return usuarioGateway.buscarUsuario(identificador);
    }
}
