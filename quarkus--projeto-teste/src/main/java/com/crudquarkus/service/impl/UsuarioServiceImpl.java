package com.crudquarkus.service.impl;

import com.crudquarkus.components.PasswordComponents;
import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.exception.BussinessException;
import com.crudquarkus.gateway.UsuarioGateway;
import com.crudquarkus.models.request.UsuarioContractRequest;
import com.crudquarkus.models.request.UsuarioCredencialRequest;
import com.crudquarkus.models.response.UsuarioContractResponse;
import com.crudquarkus.service.UsuarioService;
import com.crudquarkus.service.converter.DateConverter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Date;
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
        usuarioEntity.setSenha(PasswordComponents.criptografarSenha(request.getSenha()));
        Date dataConvertida = DateConverter.StringToDate(request.getDataNascimento());
        usuarioEntity.setDataNascimento(dataConvertida);

        return usuarioEntity;
    }

    @Override
    public UsuarioContractResponse buscarUsuario(String identificador) {
        UsuarioEntity usuarioEntity = usuarioGateway.buscarUsuario(identificador);
        return new UsuarioContractResponse(usuarioEntity);
    }

    @Override
    public void validarCredenciais(UsuarioCredencialRequest credencialRequest) {
        validateFieldCredencialRequest(credencialRequest);
        UsuarioEntity usuarioEntity = usuarioGateway.buscarUsuario(credencialRequest.getCpf());
        boolean senhavalida = PasswordComponents.isSenhaCorreta(usuarioEntity.getSenha(), credencialRequest.getSenha());
        if(!senhavalida){
            throw new BussinessException("Senha Incorreta");
        }
    }

    public void excluirUsuario(String identificador) {
        usuarioGateway.excluirUsuario(identificador);
    }

    private void validateFieldCredencialRequest(UsuarioCredencialRequest credencialRequest) {
        Set<ConstraintViolation<UsuarioCredencialRequest>> violations = validator.validate(credencialRequest);
        if(!violations.isEmpty()) {
            String message = ((ConstraintViolation) violations.toArray()[0]).getMessageTemplate();
            throw new BussinessException(message);
        }
    }


}
