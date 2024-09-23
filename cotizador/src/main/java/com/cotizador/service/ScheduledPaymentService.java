package com.cotizador.service;

import com.cotizador.entity.ScheduledPayment;

import java.util.List;

public interface ScheduledPaymentService {
    List<ScheduledPayment> findAll();

    List<ScheduledPayment> find(int paymentCalculatorId);
    ScheduledPayment save(ScheduledPayment theScheduledPayment);
    void delete(int paymentCalculatorId);
}
