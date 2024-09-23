package com.cotizador.dao;

import com.cotizador.entity.ScheduledPayment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScheduledPaymentDAOJpaImp implements ScheduledPaymentDAO{
    private EntityManager entityManager;

    @Autowired
    public ScheduledPaymentDAOJpaImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<ScheduledPayment> findAll() {
        TypedQuery<ScheduledPayment> tablaAmortizacion=entityManager.createQuery("from ScheduledPayment",ScheduledPayment.class);
        return tablaAmortizacion.getResultList();
    }

    @Override
    public List<ScheduledPayment> find(int paymentCalculatorId) {
        TypedQuery<ScheduledPayment> theQuery=entityManager.createQuery("from ScheduledPayment where paymentCalculatorId=:pPaymentCalculatorId",ScheduledPayment.class);
        theQuery.setParameter("pPaymentCalculatorId",paymentCalculatorId);

        List<ScheduledPayment> theScheduledPayment = null;
        try {
            theScheduledPayment = theQuery.getResultList();
        } catch (Exception e) {
            theScheduledPayment = null;
        }

        return theScheduledPayment;
    }

    @Override
    public ScheduledPayment save(ScheduledPayment theScheduledPayment) {
        ScheduledPayment dbscheduledPayment=entityManager.merge(theScheduledPayment);
        return entityManager.merge(dbscheduledPayment);
    }

    @Override
    public void delete(int paymentCalculatorId) {
        ScheduledPayment scheduledPayment=entityManager.find(ScheduledPayment.class,paymentCalculatorId);
        entityManager.remove(scheduledPayment);
    }
}
