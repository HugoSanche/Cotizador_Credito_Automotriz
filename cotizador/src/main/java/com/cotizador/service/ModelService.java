package com.cotizador.service;

import com.cotizador.entity.Models;

import java.util.List;

public interface ModelService {
    List<Models> findAll();
    List<Models> findByBrandId(int theId);
    List<Models> findByModelId(int theId);
    void update(Models models);
    void save(Models models);
    void delete(int theId);
    String getName(int modelId,int brandId);

}
