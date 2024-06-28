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
    public List<PaymentDay> findByDayToExecute(boolean value) {

        //In DB table paymentday the field 'status' is active and execute=1 is the day to use
        TypedQuery<PaymentDay> query=entityManager.createQuery("from PaymentDay where execute=:theData and status=:status", PaymentDay.class);

        //set parameter
        query.setParameter("theData",value);
        query.setParameter("status","Active");
        return query.getResultList();
    }

    @Override
    public List<PaymentDay> findByDay(int day) {
        //get list results
        //create a query
        TypedQuery<PaymentDay> theQuery=entityManager.createQuery("from PaymentDay where paymentDay=:theData", PaymentDay.class);

        //set query parameters
        theQuery.setParameter("theData",day);

        return theQuery.getResultList();
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
