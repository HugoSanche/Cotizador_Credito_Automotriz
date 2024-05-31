package com.cotizador.dao;

import com.cotizador.entity.Individual;

import java.util.List;

public interface IndividualDAO {
    List<Individual> findAll();
    Individual findById(int id);
    void save(Individual individual);
    void update(Individual individual);
    void delete(int id);
}
