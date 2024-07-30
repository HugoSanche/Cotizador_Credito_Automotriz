package com.cotizador.dao;

import com.cotizador.entity.Taxes;

import java.util.List;

public interface TaxesDAO {
    List<Taxes> findAll();
    Taxes findById(int theId);
    Taxes findByName(String nameTaxe);
    void update(Taxes taxes);
    void save(Taxes taxes);
    void delete(int theId);
}
