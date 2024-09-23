package com.cotizador.dao;

import com.cotizador.entity.ScheduledPayment;

import java.util.List;

public interface ScheduledPaymentDAO {
    List<ScheduledPayment> findAll();

    List<ScheduledPayment> find(int paymentCalculatorId);
    ScheduledPayment save(ScheduledPayment theScheduledPayment);
    void delete(int paymentCalculatorId);

}
