package com.crudquarkus.datasource.repository;

import com.crudquarkus.datasource.entity.UsuarioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<UsuarioEntity> {

    @Inject
    EntityManager em;

    @Transactional
    public void cadastrarUsuario(UsuarioEntity novoUsuario) {
        em.persist(novoUsuario);
    }


    public UsuarioEntity buscarPeloCpfCnpj(String cpf){
        return find("cpf", cpf).firstResult();
    }

    public UsuarioEntity buscaPeloEmail(String email){
        UsuarioEntity user = find("email", email).firstResult();
        System.out.println(user);
        return user;
    }

}
