package com.crudquarkus.service.impl;

import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.exception.BussinessException;
import com.crudquarkus.gateway.UsuarioGateway;
import com.crudquarkus.models.request.UsuarioContractRequest;
import com.crudquarkus.service.UsuarioService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    Validator validator;
    UsuarioGateway usuarioGateway;

    @Inject
    public UsuarioServiceImpl(Validator validator, UsuarioGateway usuarioGateway) {
        this.validator = validator;
        this.usuarioGateway = usuarioGateway;
    }

    public void cadastrarUsuario(UsuarioContractRequest usuarioContractRequest) {
        Set<ConstraintViolation<UsuarioContractRequest>> violations = validator.validate(usuarioContractRequest);
        if(violations.isEmpty()) {
            usuarioGateway.cadastrarUsuario(usuarioContractRequest.toUsuarioEntity());
        }
        else {
            String message = ( (ConstraintViolation) violations.toArray()[0]).getMessageTemplate();
            throw new BussinessException(message);
        }
    }

    @Override
    public UsuarioEntity buscarUsuario(String identificador) {
            return usuarioGateway.buscarUsuario(identificador);
    }
}
