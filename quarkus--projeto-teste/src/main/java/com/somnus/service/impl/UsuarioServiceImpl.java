package com.somnus.service.impl;

import com.somnus.datasource.entity.UsuarioEntity;
import com.somnus.exception.BussinessException;
import com.somnus.gateway.UsuarioGateway;
import com.somnus.models.request.UsuarioContractRequest;
import com.somnus.service.UsuarioService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    Validator validator;
    @Inject
    UsuarioGateway usuarioGateway;


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
        try {
            return usuarioGateway.buscarUsuario(identificador);
        }catch (NullPointerException e) {
            throw new BussinessException("Ocorreu algum erro ao buscar usuario: " + e.getMessage());
        }
    }
}
