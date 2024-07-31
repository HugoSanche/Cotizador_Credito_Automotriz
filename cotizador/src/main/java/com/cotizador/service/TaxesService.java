package com.cotizador.service;

import com.cotizador.entity.Taxes;

import java.util.List;

public interface TaxesService {
    List<Taxes> findAll();
    Taxes findById(int theId);
    Taxes findByName(String nameTaxe);
    void update(Taxes taxes);
    void save(Taxes taxes);
    void delete(int theId);
}
