package com.cotizador.dao;

import com.cotizador.entity.Brands;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BrandsDAOImp implements  BrandsDAO{
    EntityManager entityManager;

    public BrandsDAOImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Brands> findAll() {
        TypedQuery<Brands> query=entityManager.createQuery("from Brands", Brands.class);
        return query.getResultList();
    }

    @Override
    public Brands findById(int theId) {
        Brands brands=entityManager.find(Brands.class,theId);
        return brands;
    }

    @Override
    public void update(Brands brands) {
        entityManager.merge(brands);
    }

    @Override
    public void save(Brands brands) {
        entityManager.persist(brands);
    }

    @Override
    public void delete(int theId) {
        Brands brands=entityManager.find(Brands.class,theId);
        entityManager.remove(brands);
    }
}
