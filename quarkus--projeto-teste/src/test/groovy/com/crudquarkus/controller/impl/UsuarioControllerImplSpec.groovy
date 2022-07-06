package com.crudquarkus.controller.impl

import com.crudquarkus.datasource.entity.UsuarioEntity
import com.crudquarkus.exception.BussinessException
import com.crudquarkus.models.request.UsuarioContractRequest
import com.crudquarkus.service.converter.DateConverter
import com.crudquarkus.service.impl.UsuarioServiceImpl
import io.quarkus.test.junit.QuarkusTest
import spock.lang.Specification

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
        response.getStatus() == 406
        response.getEntity().getSituacaoRequest() == "error"
        response.getEntity().getMensagemParaUsuario().contains("erro na camada de Business")

        and:
        usuarioService.cadastrarUsuario(requestCadastrarUsuario) >> {throw new BussinessException("erro na camada de Business")}
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
        usuarioService.buscarUsuario(identificador) >> criarUsuarioEntity(criarUsuarioContractRequest())
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
        usuarioService.buscarUsuario(identificador) >> criarUsuarioEntity(criarUsuarioContractRequest())
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

    def criarUsuarioEntity(UsuarioContractRequest request){
        def usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome(request.getNome())
        usuarioEntity.setEmail(request.getEmail())
        usuarioEntity.setLogin(request.getLogin())
        usuarioEntity.setCpf(request.getCpf())
        usuarioEntity.setDataNascimento(DateConverter.StringToDate(request.getDataNascimento()))
        usuarioEntity.setTelefone(request.getTelefone())

        return usuarioEntity
    }

}
