package com.cotizador.dao;

import com.cotizador.entity.Brands;

import java.util.List;

public interface BrandsDAO  {
    List<Brands> findAll();
    Brands findById(int theId);

    void update(Brands brands);
    Brands save(Brands brands);
    void delete(int theId);
}
