package com.cotizador.dao;

import com.cotizador.entity.Brands;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BrandsDAOImp implements  BrandsDAO{
    EntityManager entityManager;

    @Autowired
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
    public Brands save(Brands brands) {
        entityManager.persist(brands);
        return brands;
    }

    @Override
    public void delete(int theId) {
        Brands brands=entityManager.find(Brands.class,theId);
        entityManager.remove(brands);
    }

    @Override
    public String getName(int theId) {
        Brands nameOfBrand=entityManager.find(Brands.class,theId);
        return nameOfBrand.getName();
    }
}
