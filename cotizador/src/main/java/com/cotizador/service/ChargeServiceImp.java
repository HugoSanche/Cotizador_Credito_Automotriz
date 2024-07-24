package com.cotizador.service;

import com.cotizador.dao.ChargesDAO;
import com.cotizador.entity.Charges;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChargeServiceImp implements ChargeService{
    ChargesDAO chargesDAO;
    @Autowired
    public ChargeServiceImp(ChargesDAO chargesDAO) {
        this.chargesDAO = chargesDAO;
    }
    @Override
    public List<Charges> findAll() {
        return chargesDAO.findAll();
    }
    @Override
    public Charges findById(int theId) {
        return chargesDAO.findById(theId);
    }
    @Override
    public Charges findByName(String nameCharge) {

        return chargesDAO.findByName(nameCharge);
    }
    @Override
    public void update(Charges charges) {
        chargesDAO.update(charges);
    }
    @Override
    public void save(Charges charges) {
        chargesDAO.save(charges);
    }
    @Override
    public void delete(int theId) {
        chargesDAO.delete(theId);
    }
}
