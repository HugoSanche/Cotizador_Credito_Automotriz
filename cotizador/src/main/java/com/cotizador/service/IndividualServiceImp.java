package com.cotizador.service;

import com.cotizador.dao.IndividualDAO;
import com.cotizador.entity.Individual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IndividualServiceImp implements IndividualService {
    IndividualDAO individualDAO;

    @Autowired
    public IndividualServiceImp(IndividualDAO individualDAO) {
        this.individualDAO = individualDAO;
    }

    @Override
    public List<Individual> findAll() {
        return individualDAO.findAll();
    }

    @Override
    public Individual findById(int id) {
        return individualDAO.findById(id);
    }

    @Transactional
    @Override
    public void save(Individual individual) {
        individualDAO.save(individual);
    }

    @Transactional
    @Override
    public void update(Individual individual) {
        individualDAO.update(individual);
    }

    @Transactional
    @Override
    public void delete(int id) {
        individualDAO.delete(id);
    }

}
