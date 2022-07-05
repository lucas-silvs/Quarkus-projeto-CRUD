package com.crudquarkus.models.response;

public class ContractResponse {

    private final String origin;
    private final String situacaoRequest;
    private final String mensagemParaUsuario;

    public ContractResponse(String origin, String situacaoRequest, String mensagemParaUsuario) {
        this.origin = origin;
        this.situacaoRequest = situacaoRequest;
        this.mensagemParaUsuario = mensagemParaUsuario;
    }

    public String getOrigin() {
        return origin;
    }

    public String getSituacaoRequest() {
        return situacaoRequest;
    }

    public String getMensagemParaUsuario() {
        return mensagemParaUsuario;
    }
}
