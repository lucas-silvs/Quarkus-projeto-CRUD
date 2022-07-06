package com.crudquarkus.service.impl;

import com.crudquarkus.components.PasswordComponents;
import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.exception.BussinessException;
import com.crudquarkus.exception.InternalException;
import com.crudquarkus.exception.ParseDataNascimentoException;
import com.crudquarkus.gateway.UsuarioGateway;
import com.crudquarkus.models.request.UsuarioContractRequest;
import com.crudquarkus.models.request.UsuarioCredencialRequest;
import com.crudquarkus.service.UsuarioService;
import com.crudquarkus.service.converter.DateConverter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
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
        validateFieldUsuarioContractRequest(usuarioContractRequest);
        UsuarioEntity entity = toUsuarioEntity(usuarioContractRequest);
        usuarioGateway.cadastrarUsuario(entity);
    }

    private void validateFieldUsuarioContractRequest(UsuarioContractRequest usuarioContractRequest) {
        Set<ConstraintViolation<UsuarioContractRequest>> violations = validator.validate(usuarioContractRequest);
        if(!violations.isEmpty()) {
            String message = ((ConstraintViolation) violations.toArray()[0]).getMessageTemplate();
            throw new BussinessException(message);
        }

    }

    private UsuarioEntity toUsuarioEntity(UsuarioContractRequest request) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome(request.getNome());
        usuarioEntity.setCpf(request.getCpf());
        usuarioEntity.setEmail(request.getEmail());
        usuarioEntity.setTelefone(request.getTelefone());
        usuarioEntity.setLogin(request.getLogin());
        usuarioEntity.setSenha(PasswordComponents.generateSecurePassword(request.getSenha(),PasswordComponents.getSaltvalue()));
        Date dataConvertida = DateConverter.StringToDate(request.getDataNascimento());
        usuarioEntity.setDataNascimento(dataConvertida);

        return usuarioEntity;
    }

    @Override
    public UsuarioEntity buscarUsuario(String identificador) {
        return usuarioGateway.buscarUsuario(identificador);
    }

    @Override
    public void validarCredenciais(UsuarioCredencialRequest credencialRequest) {
        validateFieldCredencialRequest(credencialRequest);
        UsuarioEntity usuarioEntity = usuarioGateway.buscarUsuario(credencialRequest.getCpf());
        boolean senhavalida = PasswordComponents.verifyUserPassword(credencialRequest.getSenha(), usuarioEntity.getSenha(), PasswordComponents.getSaltvalue());
        if(!senhavalida){
            throw new BussinessException("Senha Incorreta");
        }
    }

    private void validateFieldCredencialRequest(UsuarioCredencialRequest credencialRequest) {
        Set<ConstraintViolation<UsuarioCredencialRequest>> violations = validator.validate(credencialRequest);
        if(!violations.isEmpty()) {
            String message = ((ConstraintViolation) violations.toArray()[0]).getMessageTemplate();
            throw new BussinessException(message);
        }
    }


}
