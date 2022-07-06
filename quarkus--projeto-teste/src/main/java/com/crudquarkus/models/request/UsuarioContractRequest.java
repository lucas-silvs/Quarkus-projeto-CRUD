package com.crudquarkus.models.request;

import com.crudquarkus.components.PasswordComponents;
import com.crudquarkus.datasource.entity.UsuarioEntity;
import com.crudquarkus.exception.ParseDataNascimentoException;

import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UsuarioContractRequest {
    @NotBlank(message = "campo não pode ser em branco")
    private final String login;
    @NotBlank(message = "campo não pode ser em branco")
    private final String nome;
    @NotBlank(message = "campo não pode ser em branco")
    private final String email;
    @NotBlank(message = "campo não pode ser em branco")
    private final String cpf;
    @NotBlank(message = "campo não pode ser em branco")
    private final String dataNascimento;
    @NotBlank(message = "campo não pode ser em branco")
    private final String telefone;
    @NotBlank(message = "campo não pode ser em branco")
    private final String senha;

    public UsuarioContractRequest(String nome, String email, String login, String cpf, String dataNascimento, String telefone, String senha) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getCpf() {
        return cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getSenha() {
        return senha;
    }

    public UsuarioEntity toUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome(this.nome);
        usuarioEntity.setCpf(this.cpf);
        usuarioEntity.setEmail(this.email);
        usuarioEntity.setTelefone(this.telefone);
        usuarioEntity.setLogin(this.login);
        usuarioEntity.setSenha(PasswordComponents.generateSecurePassword(this.getSenha(),PasswordComponents.getSaltvalue()));
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataConverter;
        try {
            dataConverter = formato.parse(this.dataNascimento);
        } catch (ParseException e) {
            throw new ParseDataNascimentoException("Não foi possivel converter a data, erro: " + e);
        }
        usuarioEntity.setDataNascimento(dataConverter);

        return usuarioEntity;
    }
}