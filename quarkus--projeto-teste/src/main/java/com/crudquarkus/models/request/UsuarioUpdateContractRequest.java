package com.crudquarkus.models.request;

import javax.validation.constraints.NotBlank;

public class UsuarioUpdateContractRequest {

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

    public UsuarioUpdateContractRequest(String login, String nome, String email, String cpf, String dataNascimento, String telefone, String senha) {
        this.login = login;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
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
}
