package com.cotizador.service;

import com.cotizador.entity.PaymentCalculator;

import java.util.List;

public interface PaymentCalculatorService {
    List<PaymentCalculator> findAll();
    PaymentCalculator findById(int id);

    void save(PaymentCalculator paymentCalculator);
    void delete(int id);
    void update(PaymentCalculator paymentCalculator);
}
