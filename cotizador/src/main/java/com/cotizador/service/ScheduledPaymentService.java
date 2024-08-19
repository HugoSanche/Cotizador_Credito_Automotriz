package com.cotizador.service;

import com.cotizador.entity.ScheduledPayment;

import java.util.List;

public interface ScheduledPaymentService {
    List<ScheduledPayment> findAll();

    ScheduledPayment find(int contrato, int anexo);
    ScheduledPayment save(ScheduledPayment theScheduledPayment);
    void delete(int contrato, int anexo);
}
