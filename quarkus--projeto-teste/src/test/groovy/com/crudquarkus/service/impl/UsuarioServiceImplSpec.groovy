package com.crudquarkus.service.impl

import com.crudquarkus.datasource.entity.UsuarioEntity
import com.crudquarkus.exception.BussinessException
import com.crudquarkus.gateway.impl.UsuarioGatewayImpl
import com.crudquarkus.models.request.UsuarioContractRequest
import spock.lang.Specification

import javax.validation.Validation

class UsuarioServiceImplSpec extends Specification {

    def validator = Validation.buildDefaultValidatorFactory().getValidator()
    def usuarioGateway = Mock(UsuarioGatewayImpl)
    private UsuarioServiceImpl usuarioService = new UsuarioServiceImpl(validator,usuarioGateway)

    def "CadastrarUsuario - ao cadastrar usuario - e o usuario passar corretamente no validador - o usuario e cadastrado com sucesso "() {
        given:
        def usuarioContractRequest = criarUsuarioContractRequest()

        when:
        usuarioService.cadastrarUsuario(usuarioContractRequest)

        then:
        1 * usuarioGateway.cadastrarUsuario(_)

    }
    def "Cadastrar Usuario - ao cadastrar usuario na base - se os dados estiverem incorretos - deve-se lancar uma excessao"(){
        given:
        def usuarioContractRequest = criarUsuarioContractRequestInvalido()


        when:
        usuarioService.cadastrarUsuario(usuarioContractRequest)

        then:
        thrown(BussinessException)

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

    def criarUsuarioContractRequestInvalido(){
        def nomeUsuarioTeste = "batatinha"
        def loginUsuarioTeste = "batata"
        def emailUsuarioteste = "batata@email.com"
        def dataNascimentoUsuarioTeste = null
        def telefoneUsuarioTeste = null
        def cpfUsuarioTeste = null
        def senhaUsuarioTeste = null
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

    def "BuscarUsuario - ao buscar usuario - se o usuario for encontrado - deve-se retornar o usuario com seus dados"() {

        given:
        def identificador = "123234456"
        def usuarioEntity = criarUsuarioEntity(identificador)
        when:
        def response = usuarioService.buscarUsuario(identificador)

        then:
        response != null
        response.getCpf() == identificador
        response.getLogin() == "loginTeste"

        and:
        usuarioGateway.buscarUsuario(identificador) >> usuarioEntity
    }

    def criarUsuarioEntity(String identificador){
        def usuario = new UsuarioEntity()
        usuario.setLogin("loginTeste")
        usuario.setCpf(identificador)
        return usuario
    }


}
