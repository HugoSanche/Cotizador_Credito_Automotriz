package com.cotizador.service;

import com.cotizador.entity.Brands;

import java.util.List;

public interface BrandService {
    List<Brands> findAll();
    Brands findById(int theId);

    void update(Brands brands);
    void save(Brands brands);
    void delete(int theId);
    String getName(int theId);
}
