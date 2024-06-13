package com.cotizador.dao;

import com.cotizador.entity.PaymentCalculator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PaymentcalculatorDAOImp implements  PaymentcalculatorDAO{
    EntityManager entityManager;

    public PaymentcalculatorDAOImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<PaymentCalculator> findAll() {
        TypedQuery<PaymentCalculator> query=entityManager.createQuery("from Paymentcalculator", PaymentCalculator.class);
        return query.getResultList();
    }

    @Override
    public PaymentCalculator findById(int id) {
        return entityManager.find(PaymentCalculator.class,id);
    }

    @Override
    public void save(PaymentCalculator paymentcalculator) {
        entityManager.persist(paymentcalculator);
    }

    @Override
    public void update(PaymentCalculator paymentcalculator) {
        entityManager.merge(paymentcalculator);
    }

    @Override
    public void delete(int id) {
        PaymentCalculator paymentcalculator=entityManager.find(PaymentCalculator.class,id);
        entityManager.merge(paymentcalculator);
        entityManager.remove(paymentcalculator);
    }
}
