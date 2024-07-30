package com.cotizador.dao;

import com.cotizador.entity.Taxes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaxesDAOImp implements TaxesDAO{
    EntityManager entityManager;

    @Autowired
    public TaxesDAOImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Taxes> findAll() {
        TypedQuery<Taxes> taxesTypedQuery=entityManager.createQuery(" from Taxes", Taxes.class);
        return taxesTypedQuery.getResultList();
    }

    @Override
    public Taxes findById(int theId) {
        Taxes taxes=entityManager.find(Taxes.class,theId);
        return taxes;
    }

    @Override
    public Taxes findByName(String nameTaxes) {
        TypedQuery<Taxes> taxes=entityManager.createQuery("from Taxes where name=:theData", Taxes.class);
        taxes.setParameter("theData",nameTaxes);
        return taxes.getResultList().get(0);
    }

    @Override
    public void update(Taxes taxes) {
        entityManager.merge(taxes);
    }

    @Override
    public void save(Taxes taxes)
    {
        entityManager.persist(taxes);
    }

    @Override
    public void delete(int theId) {
        Taxes taxes=entityManager.find(Taxes.class,theId);
        entityManager.remove(taxes);
    }
}
