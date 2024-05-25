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
    public ScheduledPayment find(int contrato, int anexo) {
        TypedQuery<ScheduledPayment> theQuery=entityManager.createQuery("from ScheduledPayment where contrato=:pContrato and anexo=:pAnexo",ScheduledPayment.class);
        theQuery.setParameter("pContrato",contrato);
        theQuery.setParameter("pAnexo",anexo);
        ScheduledPayment theScheduledPayment = null;
        try {
            theScheduledPayment = theQuery.getSingleResult();
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
    public void delete(int contrato, int anexo) {

    }
}
