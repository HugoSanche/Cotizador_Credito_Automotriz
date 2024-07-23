package com.cotizador.service;

import com.cotizador.dao.ModelsDAO;
import com.cotizador.entity.Models;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImp implements ModelService{

    ModelsDAO modelsDAO;

    @Autowired
    public ModelServiceImp(ModelsDAO modelsDAO) {
        this.modelsDAO = modelsDAO;
    }

    @Override
    public List<Models> findAll() {
        return modelsDAO.findAll();
    }

    @Override
    public List<Models> findByBrandId(int theId) {
        return modelsDAO.findByBrandId(theId);
    }

    @Override
    public List<Models> findByModelId(int theId) {
        return modelsDAO.findByModelId(theId);
    }

    @Override
    public void update(Models models) {
        modelsDAO.update(models);
    }

    @Override
    public void save(Models models) {
        modelsDAO.save(models);
    }

    @Override
    public void delete(int theId) {
        modelsDAO.delete(theId);
    }
}
