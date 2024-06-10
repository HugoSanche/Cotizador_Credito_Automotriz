package com.cotizador.dao;

import com.cotizador.entity.Paymentcalculator;

import java.util.List;

public interface PaymentcalculatorDAO {
    List<Paymentcalculator> findAll();
    Paymentcalculator findById(int id);
    void save(Paymentcalculator paymentcalculator);
    void update(Paymentcalculator paymentcalculator);
    void delete(int id);
}
