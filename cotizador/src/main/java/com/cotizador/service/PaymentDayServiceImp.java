package com.cotizador.service;

import com.cotizador.dao.PaymentDayDAO;
import com.cotizador.entity.PaymentDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PaymentDayServiceImp implements PaymentDayService{
PaymentDayDAO paymentDayDAO;

@Autowired
    public PaymentDayServiceImp(PaymentDayDAO paymentDayDAO) {
        this.paymentDayDAO = paymentDayDAO;
    }

    @Override
    public List<PaymentDay> findAll() {
        return paymentDayDAO.findAll();
    }

    @Override
    public PaymentDay findById(int theId) {
        return paymentDayDAO.findById(theId);
    }

    @Override
    public List<PaymentDay> findByDayToExecute(boolean value) {
        return paymentDayDAO.findByDayToExecute(value);
    }

    @Override
    public List<PaymentDay> findByDay(int day) {
        List<PaymentDay>listPaymentDay= paymentDayDAO.findByDay(day);
        return listPaymentDay;
    }

    @Transactional
    @Override
    public void update(PaymentDay paymentDay) {
        paymentDayDAO.update(paymentDay);
    }

    @Transactional
    @Override
    public void save(PaymentDay paymentDay) {
        paymentDayDAO.save(paymentDay);
    }

    @Transactional
    @Override
    public void delete(int theId) {
        paymentDayDAO.delete(theId);
    }
}
