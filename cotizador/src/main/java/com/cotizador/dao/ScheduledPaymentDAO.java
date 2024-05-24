package com.cotizador.dao;

import com.cotizador.entity.ScheduledPayment;

import java.util.List;

public interface ScheduledPaymentDAO {
    List<ScheduledPayment> findAll();

    ScheduledPayment find(int contrato, int anexo);
    ScheduledPayment save(ScheduledPayment theScheduledPayment);
    void delete(int contrato, int anexo);

}
