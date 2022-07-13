package com.crudquarkus.gateway.impl

import com.crudquarkus.datasource.entity.UsuarioEntity
import com.crudquarkus.datasource.repository.UsuarioRepository
import com.crudquarkus.exception.LayerException
import spock.lang.Specification

import javax.ws.rs.NotFoundException

class UsuarioGatewayImplSpec extends Specification {

    def usuarioRepositoryMock = Mock(UsuarioRepository)

    private UsuarioGatewayImpl usuarioGateway = new UsuarioGatewayImpl(usuarioRepositoryMock)

    def "Cadastrar Usuario - ao cadastrar usuario na base - se os dados estiverem corretos e o usuario ainda nao existir na base - deve cadastrar o usuario com sucesso"(){
        given:
        def identificador = "123123123"
        def usuarioEntity = criarUsuarioEntity(identificador)

        when:
        usuarioGateway.cadastrarUsuario(usuarioEntity)

        then:
        1* usuarioRepositoryMock.cadastrarUsuario(usuarioEntity)
    }

    def "Cadastrar Usuario - ao cadastrar usuario na base - se o usuario existir na base - deve-se lancar uma excessao"(){
        given:
        def identificador = "123123123"
        def usuarioEntity = criarUsuarioEntity(identificador)
        when:
        usuarioGateway.cadastrarUsuario(usuarioEntity)

        then:
        thrown(LayerException)

        and:
        usuarioRepositoryMock.buscarPeloCpfCnpj(usuarioEntity.getCpf()) >> new UsuarioEntity()
    }

    def "Buscar Usuario - au receber um identificador - se o identificador do usuario existir na base - deve-se retornar o usuario"(){
        given:
        def identificador = "123123123"
        def usuarioEntity = criarUsuarioEntity(identificador)

        when:
        def response = usuarioGateway.buscarUsuario(identificador)

        then:
        response != null
        response.getCpf() == identificador

        and:
        usuarioRepositoryMock.buscarPeloCpfCnpj(identificador) >> usuarioEntity
    }

    def "Buscar Usuario - au receber um identificador - se o identificador do usuario nao existir na base - deve-se lancar uma excessao"(){
        given:
        def identificador = "123123123"

        when:
        usuarioGateway.buscarUsuario(identificador)

        then:
        thrown(LayerException)

        and:
        usuarioRepositoryMock.buscarPeloCpfCnpj(identificador) >> { throw new NotFoundException() }
    }

    def criarUsuarioEntity(String identificador){
        def usuario = new UsuarioEntity()
        usuario.setCpf(identificador)
        usuario.setNome("UsuarioTeste")
        usuario.setLogin("loginTeste")
        usuario.setEmail("emailTeste@email.com")
        usuario.setDataNascimento(new Date())
        usuario.setTelefone("123456789")
        return usuario
    }
}
