package com.cotizador.dao;

import com.cotizador.entity.PaymentCalculator;

import java.util.List;

public interface PaymentcalculatorDAO {
    List<PaymentCalculator> findAll();
    PaymentCalculator findById(int id);
    void save(PaymentCalculator paymentcalculator);
    void update(PaymentCalculator paymentcalculator);
    void delete(int id);
}
