package com.cotizador.dao;

import com.cotizador.entity.Paymentcalculator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PaymentcalculatorDAOImp implements  PaymentcalculatorDAO{
    EntityManager entityManager;

    public PaymentcalculatorDAOImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Paymentcalculator> findAll() {
        TypedQuery<Paymentcalculator> query=entityManager.createQuery("from Paymentcalculator", Paymentcalculator.class);
        return query.getResultList();
    }

    @Override
    public Paymentcalculator findById(int id) {
        return entityManager.find(Paymentcalculator.class,id);
    }

    @Override
    public void save(Paymentcalculator paymentcalculator) {
        entityManager.persist(paymentcalculator);
    }

    @Override
    public void update(Paymentcalculator paymentcalculator) {
        entityManager.merge(paymentcalculator);
    }

    @Override
    public void delete(int id) {
        Paymentcalculator paymentcalculator=entityManager.find(Paymentcalculator.class,id);
        entityManager.merge(paymentcalculator);
        entityManager.remove(paymentcalculator);
    }
}
