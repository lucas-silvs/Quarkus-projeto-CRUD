package com.crudquarkus.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UsuarioCredencialTecladoVirtualRequest {
    @NotBlank(message = "campo não pode ser em branco")
    private String cpf;

    @NotNull
    private char[][] tecladoVirtual;

    @NotNull(message = "campo não pode ser em branco")
    private int[] teclasPresionadas;

    public char[][] getTecladoVirtual() {
        return tecladoVirtual;
    }

    public void setTecladoVirtual(char[][] tecladoVirtual) {
        this.tecladoVirtual = tecladoVirtual;
    }

    public int[] getTeclasPresionadas() {
        return teclasPresionadas;
    }

    public void setTeclasPresionadas(int[] teclasPresionadas) {
        this.teclasPresionadas = teclasPresionadas;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
