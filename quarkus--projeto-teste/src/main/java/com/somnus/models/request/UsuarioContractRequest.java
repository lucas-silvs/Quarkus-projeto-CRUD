package com.somnus.models.request;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.somnus.datasource.models.UsuarioEntity;
import com.somnus.exception.ParseDataNascimentoException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@JsonPropertyOrder({
        "login",
        "nome",
        "email",
        "cpf",
        "dataNascimento",
        "telefone",
        "senha"})
public class UsuarioContractRequest {

    private final String login;
    private final String nome;
    private final String email;
    private final String cpf;
    private final String dataNascimento;
    private final String telefone;
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
        usuarioEntity.setSenha(this.senha);
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
