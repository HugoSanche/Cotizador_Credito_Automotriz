package com.cotizador.service;

import com.cotizador.entity.PaymentDay;

import java.util.List;

public interface PaymentDayService {
    List<PaymentDay> findAll();
    PaymentDay findById(int theId);
    public List<PaymentDay> findByDayToExecute(boolean value);
    List<PaymentDay> findByDay(int day);
    void update(PaymentDay paymentDay);
    void save(PaymentDay paymentDay);
    void delete(int theId);
}
