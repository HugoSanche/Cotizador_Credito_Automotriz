package com.cotizador.service;

import com.cotizador.dao.TaxesDAO;
import com.cotizador.entity.Taxes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxesServiceImp implements TaxesService{

    TaxesDAO taxesDAO;

    @Autowired
    public TaxesServiceImp(TaxesDAO taxesDAO) {
        this.taxesDAO = taxesDAO;
    }

    @Override
    public List<Taxes> findAll() {
        return taxesDAO.findAll();
    }

    @Override
    public Taxes findById(int theId) {
        return taxesDAO.findById(theId);
    }

    @Override
    public Taxes findByName(String nameTaxe) {
        return taxesDAO.findByName(nameTaxe);
    }

    @Override
    public void update(Taxes taxes) {
        taxesDAO.update(taxes);
    }

    @Override
    public void save(Taxes taxes) {
        taxesDAO.save(taxes);
    }

    @Override
    public void delete(int theId) {
        taxesDAO.delete(theId);
    }
}
