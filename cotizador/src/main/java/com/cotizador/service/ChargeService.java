package com.cotizador.service;

import com.cotizador.entity.Charges;

import java.util.List;

public interface ChargeService {
    List<Charges> findAll();
    Charges findById(int theId);
    Charges findByName(String nameCharge);
    void update(Charges charges);
    void save(Charges charges);
    void delete(int theId);
}
