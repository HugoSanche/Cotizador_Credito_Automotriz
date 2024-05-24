package com.cotizador.dao;

import com.cotizador.entity.ScheduledPayment;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ScheduledPaymentDAOJpaImp implements ScheduledPaymentDAO{
    private EntityManager entityManager;

    @Autowired
    public ScheduledPaymentDAOJpaImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<ScheduledPayment> findAll() {
        return null;
    }

    @Override
    public ScheduledPayment find(int contrato, int anexo) {
        return null;
    }

    @Override
    public ScheduledPayment save(ScheduledPayment theScheduledPayment) {
        return null;
    }

    @Override
    public void delete(int contrato, int anexo) {

    }
}
