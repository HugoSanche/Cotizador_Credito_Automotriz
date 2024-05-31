package com.cotizador.service;

import com.cotizador.entity.Individual;

import java.util.List;


public interface IndividualService {

    public List<Individual> findAll();
    public Individual findById(int id);
    public void save(Individual individual);
    public void update(Individual individual);
    public void delete(int id);

}
