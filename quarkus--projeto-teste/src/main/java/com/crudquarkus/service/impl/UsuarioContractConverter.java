package com.crudquarkus.service.impl;

import com.crudquarkus.components.PasswordComponents;
import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.exception.ParseDataNascimentoException;
import com.crudquarkus.model.UsuarioContractRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsuarioContractConverter {

    private UsuarioContractConverter() {
        throw new IllegalStateException("classe de utilidade não pode ser instanciada");
    }


    public static UsuarioEntity toUsuarioEntity(UsuarioContractRequest contractRequest) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome(contractRequest.getNome());
        usuarioEntity.setCpf(contractRequest.getCpf());
        usuarioEntity.setEmail(contractRequest.getEmail());
        usuarioEntity.setTelefone(contractRequest.getTelefone());
        usuarioEntity.setLogin(contractRequest.getLogin());
        usuarioEntity.setSenha(PasswordComponents.generateSecurePassword(contractRequest.getSenha(),PasswordComponents.getSaltvalue()));
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataConverter;
        try {
            dataConverter = formato.parse(contractRequest.getDataNascimento());
        } catch (ParseException e) {
            throw new ParseDataNascimentoException("Não foi possivel converter a data, erro: " + e);
        }
        usuarioEntity.setDataNascimento(dataConverter);

        return usuarioEntity;
    }
}
