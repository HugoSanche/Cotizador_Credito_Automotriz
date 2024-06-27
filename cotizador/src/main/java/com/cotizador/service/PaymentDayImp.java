package com.cotizador.service;

import com.cotizador.entity.PaymentDay;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PaymentDayImp implements PaymentDayService{
PaymentDayService paymentDayService;

    public PaymentDayImp(PaymentDayService paymentDayService) {
        this.paymentDayService = paymentDayService;
    }

    @Override
    public List<PaymentDay> findAll() {
        return paymentDayService.findAll();
    }

    @Override
    public PaymentDay findById(int theId) {
        return paymentDayService.findById(theId);
    }

    @Override
    public void update(PaymentDay paymentDay) {
        paymentDayService.update(paymentDay);
    }

    @Override
    public void save(PaymentDay paymentDay) {
        paymentDayService.save(paymentDay);
    }

    @Override
    public void delete(int theId) {
        paymentDayService.delete(theId);
    }
}
