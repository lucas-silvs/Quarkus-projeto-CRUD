package com.crudquarkus.controller.impl


import com.crudquarkus.exception.LayerException
import com.crudquarkus.models.request.UsuarioContractRequest
import com.crudquarkus.models.response.UsuarioContractResponse
import com.crudquarkus.service.converter.DateConverter
import com.crudquarkus.service.impl.UsuarioServiceImpl
import io.quarkus.test.junit.QuarkusTest
import spock.lang.Specification

import javax.ws.rs.core.Response

@QuarkusTest
class UsuarioControllerImplSpec extends Specification {

    def usuarioService =  Mock(UsuarioServiceImpl)
    private UsuarioControllerImpl usuarioController = new UsuarioControllerImpl(usuarioService)

    def "CadastrarUsuario - ao enviar os dados do usuario para cadastro - e os dados estiverem corretos - o usuario sera cadastrado e a retornado http status 200"() {
        given:

        def requestCadastrarUsuario = criarUsuarioContractRequest()

        when:
        def response = usuarioController.cadastrarUsuario(requestCadastrarUsuario)

        then:
        response.getStatus() == 200
        response.getEntity().getSituacaoRequest() == "sucesso"
    }

    def "CadastrarUsuario - ao enviar os dados do usuario para cadastro - e ocorrer uma excessao - deve-se retornar http status 406, a situacao da request ser 'error' e a mensagem para o usuario informando o erro"() {
        given:
        def requestCadastrarUsuario = criarUsuarioContractRequest()

        when:
        def response = usuarioController.cadastrarUsuario(requestCadastrarUsuario)

        then:
        response.getStatus() == 422
        response.getEntity().getSituacaoRequest() == "error"
        response.getEntity().getMensagemParaUsuario().contains("erro na camada de Business")

        and:
        usuarioService.cadastrarUsuario(requestCadastrarUsuario) >> {throw new LayerException("Excessao", "BUSINESS", Response.Status.INTERNAL_SERVER_ERROR, "")}
    }

    def "BuscarUsuario - ao informar o cpf do usuario para busca - caso o usuario exista na base - e retornado o http status 200 e os dados do usuario"() {
        given:
        def identificador = "12345678901"

        when:
        def response = usuarioController.buscarUsuario(identificador)

        then:
        response.getStatus() == 200
        response.getEntity() != null
        response.getEntity().getCpf() == identificador

        and:
        usuarioService.buscarUsuario(identificador) >> criarUsuarioContractResponse(criarUsuarioContractRequest())
    }

    def "BuscarUsuario - ao informar o cpf do usuario para busca - caso o usuario nao exista na base - e retornado o http status 200 e os dados do usuario"() {
        given:
        def identificador = "12345678901"

        when:
        def response = usuarioController.buscarUsuario(identificador)

        then:
        response.getStatus() == 200
        response.getEntity() != null
        response.getEntity().getCpf() == identificador

        and:
        usuarioService.buscarUsuario(identificador) >> criarUsuarioContractResponse(criarUsuarioContractRequest())
    }

    def criarUsuarioContractRequest(){
        def nomeUsuarioTeste = "batatinha"
        def loginUsuarioTeste = "batata"
        def emailUsuarioteste = "batata@email.com"
        def dataNascimentoUsuarioTeste = "03/11/2000"
        def telefoneUsuarioTeste = "11111111111"
        def cpfUsuarioTeste = "12345678901"
        def senhaUsuarioTeste = "123456"
        def requestCadastrarUsuario = new UsuarioContractRequest(
                nomeUsuarioTeste,
                emailUsuarioteste,
                loginUsuarioTeste,
                cpfUsuarioTeste,
                dataNascimentoUsuarioTeste,
                senhaUsuarioTeste,
                telefoneUsuarioTeste,
        )
        return requestCadastrarUsuario
    }

    def criarUsuarioContractResponse(UsuarioContractRequest request){
        def usuarioContractResponse = new UsuarioContractResponse()
        usuarioContractResponse.setNome(request.getNome())
        usuarioContractResponse.setEmail(request.getEmail())
        usuarioContractResponse.setLogin(request.getLogin())
        usuarioContractResponse.setCpf(request.getCpf())
        usuarioContractResponse.setDataNascimento(DateConverter.StringToDate(request.getDataNascimento()))
        usuarioContractResponse.setTelefone(request.getTelefone())

        return usuarioContractResponse
    }

}
