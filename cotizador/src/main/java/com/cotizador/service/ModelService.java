package com.cotizador.service;

import com.cotizador.entity.Models;

import java.util.List;

public interface ModelService {
    List<Models> findAll();
    Models findById(int theId);

    void update(Models models);
    void save(Models models);
    void delete(int theId);

}
