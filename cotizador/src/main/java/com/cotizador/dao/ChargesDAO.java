package com.cotizador.dao;

import com.cotizador.entity.Charges;

import java.util.List;

public interface ChargesDAO {
    List<Charges> findAll();
    Charges findById(int theId);
   List<Charges> findByName(String nameCharge);
    void update(Charges charges);
    void save(Charges charges);
    void delete(int theId);
}
