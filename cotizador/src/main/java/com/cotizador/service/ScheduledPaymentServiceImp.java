package com.cotizador.service;

import com.cotizador.dao.ScheduledPaymentDAO;
import com.cotizador.entity.ScheduledPayment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ScheduledPaymentServiceImp implements ScheduledPaymentService{
    ScheduledPaymentDAO scheduledPaymentDAO;

    public ScheduledPaymentServiceImp(ScheduledPaymentDAO scheduledPaymentDAO) {
        this.scheduledPaymentDAO = scheduledPaymentDAO;
    }

    @Override
    public List<ScheduledPayment> findAll() {
        return scheduledPaymentDAO.findAll();
    }

    @Override
    public ScheduledPayment find(int contrato, int anexo) {
        return scheduledPaymentDAO.find(contrato,anexo);
    }

    @Transactional
    @Override
    public ScheduledPayment save(ScheduledPayment theScheduledPayment) {
        return scheduledPaymentDAO.save(theScheduledPayment);
    }

    @Transactional
    @Override
    public void delete(int contrato, int anexo) {

        scheduledPaymentDAO.delete(contrato,anexo);
    }
}
