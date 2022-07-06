package com.crudquarkus.models.request;

import javax.validation.constraints.NotBlank;

public class UsuarioCredencialRequest {

    @NotBlank(message = "campo não pode ser em branco")
    private final String cpf;
    @NotBlank(message = "campo não pode ser em branco")
    private final String senha;


    public UsuarioCredencialRequest(String cpf, String senha) {
        this.cpf = cpf;
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

}
