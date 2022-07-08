package com.crudquarkus.datasource.repository;

import com.crudquarkus.datasource.entity.UsuarioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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

}
