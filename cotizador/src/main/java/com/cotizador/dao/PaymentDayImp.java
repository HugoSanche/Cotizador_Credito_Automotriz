package com.cotizador.dao;

import com.cotizador.entity.PaymentDay;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PaymentDayImp implements PaymentDayDAO{
    EntityManager entityManager;

    @Autowired
    public PaymentDayImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<PaymentDay> findAll() {
        TypedQuery<PaymentDay> query=entityManager.createQuery("from paymentday",PaymentDay.class);
        return query.getResultList();
    }

    @Override
    public PaymentDay findById(int theId) {
        PaymentDay paymentDay=entityManager.find(PaymentDay.class,theId);
        return paymentDay;
    }

    @Override
    public void update(PaymentDay paymentDay) {
        entityManager.merge(paymentDay);
    }

    @Override
    public void save(PaymentDay paymentDay) {
        entityManager.persist(paymentDay);
    }

    @Override
    public void delete(int theId) {
        PaymentDay paymentDay=entityManager.find(PaymentDay.class,theId);
        entityManager.remove(paymentDay);
    }
}
