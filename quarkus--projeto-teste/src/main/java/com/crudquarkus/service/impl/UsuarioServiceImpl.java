package com.crudquarkus.service.impl;

import com.crudquarkus.components.PasswordComponents;
import com.crudquarkus.components.TecladoVirtualComponent;
import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.exception.LayerException;
import com.crudquarkus.gateway.UsuarioGateway;
import com.crudquarkus.models.request.UsuarioContractRequest;
import com.crudquarkus.models.request.UsuarioCredencialRequest;
import com.crudquarkus.models.request.UsuarioCredencialTecladoVirtualRequest;
import com.crudquarkus.models.response.UsuarioContractResponse;
import com.crudquarkus.service.UsuarioService;
import com.crudquarkus.service.converter.DateConverter;
import com.crudquarkus.service.validator.CpfValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.Response.Status;
import java.util.Date;
import java.util.Set;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    public static final String BUSINESS = "BUSINESS";
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

    @Override
    public UsuarioContractResponse buscarUsuario(String identificador) {
        validarCpf(identificador);
        UsuarioEntity usuarioEntity = usuarioGateway.buscarUsuario(identificador);
        return new UsuarioContractResponse(usuarioEntity);
    }

    @Override
    public void validarCredenciais(UsuarioCredencialRequest credencialRequest) {
        validateFieldCredencialRequest(credencialRequest);
        validarCpf(credencialRequest.getCpf());
        UsuarioEntity usuarioEntity = usuarioGateway.buscarUsuario(credencialRequest.getCpf());
        boolean senhavalida = PasswordComponents.isSenhaCorreta(usuarioEntity.getSenha(), credencialRequest.getSenha());
        if(!senhavalida){
            throw new LayerException("Senha Incorreta", BUSINESS, Status.NOT_ACCEPTABLE, "UsuarioServiceImpl.validarCredenciais()");
        }
    }

    public void excluirUsuario(String identificador) {
        usuarioGateway.excluirUsuario(identificador);
    }

    @Override
    public void atualizadDadosUsuario(UsuarioContractRequest updateContractRequest) {
        validarCpf(updateContractRequest.getCpf());
        validateFieldUsuarioContractRequest(updateContractRequest);
        usuarioGateway.atualizarDadosUsuario(updateContractRequest);

    }

    @Override
    public void validarCredenciaisTecladoVirtual(UsuarioCredencialTecladoVirtualRequest credencialTecladoVirtualRequest) {
        validateFieldCredencialTecladoVirtualRequest(credencialTecladoVirtualRequest);
        validarCpf(credencialTecladoVirtualRequest.getCpf());
        UsuarioEntity usuarioEntity = usuarioGateway.buscarUsuario(credencialTecladoVirtualRequest.getCpf());
        boolean senhavalida = TecladoVirtualComponent.isSenhaCorreta(usuarioEntity.getSenha(), credencialTecladoVirtualRequest.getTecladoVirtual(), credencialTecladoVirtualRequest.getTeclasPresionadas());
        if(!senhavalida){
            throw new LayerException("Senha Incorreta", BUSINESS, Status.NOT_ACCEPTABLE, "UsuarioServiceImpl.validarCredenciais()");
        }

    }

    public void validarCredenciaisTecladoVirtualBinario(UsuarioCredencialTecladoVirtualRequest credencialTecladoVirtualRequest) {
        validateFieldCredencialTecladoVirtualRequest(credencialTecladoVirtualRequest);
        validarCpf(credencialTecladoVirtualRequest.getCpf());
        UsuarioEntity usuarioEntity = usuarioGateway.buscarUsuario(credencialTecladoVirtualRequest.getCpf());
        boolean senhavalida = TecladoVirtualComponent.isSenhaCorretaBinario(usuarioEntity.getSenha(), credencialTecladoVirtualRequest.getTecladoVirtual(), credencialTecladoVirtualRequest.getTeclasPresionadas());
        if(!senhavalida){
            throw new LayerException("Senha Incorreta", BUSINESS, Status.NOT_ACCEPTABLE, "UsuarioServiceImpl.validarCredenciais()");
        }
    }

    public void validarCredenciaisTecladoVirtualParalelo(UsuarioCredencialTecladoVirtualRequest credencialTecladoVirtualRequest) {
        validateFieldCredencialTecladoVirtualRequest(credencialTecladoVirtualRequest);
        validarCpf(credencialTecladoVirtualRequest.getCpf());
        UsuarioEntity usuarioEntity = usuarioGateway.buscarUsuario(credencialTecladoVirtualRequest.getCpf());
        boolean senhavalida = false;

        if ( credencialTecladoVirtualRequest.getTecladoVirtualList() != null && !credencialTecladoVirtualRequest.getTecladoVirtualList().isEmpty()){
            senhavalida = TecladoVirtualComponent.isSenhaCorretaParallel(usuarioEntity.getSenha(), credencialTecladoVirtualRequest.getTecladoVirtualList(), credencialTecladoVirtualRequest.getTeclasPresionadas());
        }
        else {
            senhavalida = TecladoVirtualComponent.isSenhaCorretaParallel(usuarioEntity.getSenha(), credencialTecladoVirtualRequest.getTecladoVirtual(), credencialTecladoVirtualRequest.getTeclasPresionadas());
        }
        if(!senhavalida){
            throw new LayerException("Senha Incorreta", BUSINESS, Status.NOT_ACCEPTABLE, "UsuarioServiceImpl.validarCredenciais()");
        }
    }


    private void validateFieldCredencialTecladoVirtualRequest(UsuarioCredencialTecladoVirtualRequest credencialRequest) {
        Set<ConstraintViolation<UsuarioCredencialTecladoVirtualRequest>> violations = validator.validate(credencialRequest);
        if (!violations.isEmpty()) {
            String message = ((ConstraintViolation) violations.toArray()[0]).getMessageTemplate();
            throw new LayerException(message, BUSINESS, Status.NOT_ACCEPTABLE, "UsuarioServiceImpl.validarCredenciaisTecladoVirtual()");
        }
    }


    private void validateFieldUsuarioContractRequest(UsuarioContractRequest usuarioContractRequest) {
        Set<ConstraintViolation<UsuarioContractRequest>> violations = validator.validate(usuarioContractRequest);
        if(!violations.isEmpty()) {
            String message = ((ConstraintViolation) violations.toArray()[0]).getMessageTemplate();
            throw new LayerException(message, BUSINESS, Status.NOT_ACCEPTABLE, "UsuarioServiceImpl.validateFieldUsuarioContractRequest()");
        }
        CpfValidator.isCPF(usuarioContractRequest.getCpf());

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

    private void validarCpf(String cpf){
        if(!CpfValidator.isCPF(cpf)){
            throw new LayerException("Identificador Cpf Invalido", BUSINESS, Status.NOT_ACCEPTABLE, "UsuarioServiceImpl.validarCpf()");
        }
    }

    private void validateFieldCredencialRequest(UsuarioCredencialRequest credencialRequest) {
        Set<ConstraintViolation<UsuarioCredencialRequest>> violations = validator.validate(credencialRequest);
        if(!violations.isEmpty()) {
            String message = ((ConstraintViolation) violations.toArray()[0]).getMessageTemplate();
            throw new LayerException(message, BUSINESS, Status.NOT_ACCEPTABLE, "UsuarioServiceImpl.validateFieldCredencialRequest()");
        }
    }


}
