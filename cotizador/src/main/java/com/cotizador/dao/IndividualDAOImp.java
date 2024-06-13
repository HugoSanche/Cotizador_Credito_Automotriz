package com.cotizador.dao;

import com.cotizador.entity.Individual;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IndividualDAOImp implements IndividualDAO{
    private EntityManager entityManager;

    @Autowired
    public IndividualDAOImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Individual> findAll() {
        TypedQuery<Individual> employees=entityManager.createQuery("from Individual order by firstName", Individual.class);
        return employees.getResultList();
    }

    @Override
    public Individual findById(int id) {
        Individual individual=entityManager.find(Individual.class,id);
        return individual;
    }

    @Override
    public void save(Individual individual) {
        entityManager.persist(individual);
    }

    @Override
    public void update(Individual individual) {
        entityManager.merge(individual);
    }

    @Override
    public void delete(int id) {
       Individual individual=entityManager.find(Individual.class,id);
       entityManager.merge(individual);
       entityManager.remove(individual);
    }
}
