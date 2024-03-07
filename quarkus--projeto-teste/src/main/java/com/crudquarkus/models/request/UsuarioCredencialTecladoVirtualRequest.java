package com.crudquarkus.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class UsuarioCredencialTecladoVirtualRequest {
    @NotBlank(message = "campo não pode ser em branco")
    private String cpf;

    private char[][] tecladoVirtual;

    private List<List<Character>> tecladoVirtualList;

    @NotNull(message = "campo não pode ser em branco")
    private int[] teclasPresionadas;

    public List<List<Character>> getTecladoVirtualList() {
        return tecladoVirtualList;
    }

    public void setTecladoVirtualList(List<List<Character>> tecladoVirtualList) {
        this.tecladoVirtualList = tecladoVirtualList;
    }

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
