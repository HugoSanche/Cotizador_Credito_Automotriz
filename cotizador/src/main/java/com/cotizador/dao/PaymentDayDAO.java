package com.cotizador.dao;

import com.cotizador.entity.PaymentDay;

import java.util.List;

public interface PaymentDayDAO {
    List<PaymentDay> findAll();
    PaymentDay findById(int theId);

    //find the day was assigned to use
    List<PaymentDay> findByDayToExecute(boolean value);
    List<PaymentDay> findByDay(int day);
    void update(PaymentDay paymentDay);
    void save(PaymentDay paymentDay);
    void delete(int theId);

}
