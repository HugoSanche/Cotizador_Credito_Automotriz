package com.cotizador.dao;

import com.cotizador.entity.Models;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModelsDAOImp implements  ModelsDAO{
    EntityManager entityManager;

    @Autowired
    public ModelsDAOImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Models> findAll() {
        TypedQuery<Models> query=entityManager.createQuery("from Models",Models.class);
        return query.getResultList();
    }

    @Override
    public Models findById(int theId) {
        Models models =entityManager.find(Models.class,theId);
        return models;
    }

    @Override
    public void update(Models models) {
        entityManager.merge(models);
    }

    @Override
    public void save(Models models) {
        entityManager.persist(models);
    }

    @Override
    public void delete(int theId) {
        Models models=entityManager.find(Models.class,theId);
        entityManager.remove(models);
    }
}