package com.cotizador.service;

import com.cotizador.dao.PaymentcalculatorDAO;
import com.cotizador.entity.PaymentCalculator;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class PaymentCalculatorServiceImp  implements PaymentCalculatorService{

    PaymentcalculatorDAO paymentcalculatorDAO;
    @Override
    public List<PaymentCalculator> findAll() {
        return paymentcalculatorDAO.findAll();
    }

    @Override
    public PaymentCalculator findById(int id) {
        return paymentcalculatorDAO.findById(id);
    }

    @Transactional
    @Override
    public void save(PaymentCalculator paymentCalculator) {
        paymentcalculatorDAO.save(paymentCalculator);
    }

    @Transactional
    @Override
    public void delete(int id) {
        paymentcalculatorDAO.delete(id);
    }
    @Transactional
    @Override
    public void update(PaymentCalculator paymentCalculator) {
        paymentcalculatorDAO.update(paymentCalculator);
    }
}
