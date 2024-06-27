package com.cotizador.dao;

import com.cotizador.entity.PaymentDay;

import java.util.List;

public interface PaymentDayDAO {
    List<PaymentDay> findAll();
    PaymentDay findById(int theId);
    void update(PaymentDay paymentDay);
    void save(PaymentDay paymentDay);
    void delete(int theId);
}
