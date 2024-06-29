package com.cotizador.service;

import com.cotizador.dao.BrandsDAO;
import com.cotizador.entity.Brands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImp implements  BrandService{
    BrandsDAO brandsDAO;

    @Autowired
    public BrandServiceImp(BrandsDAO brandsDAO) {
        this.brandsDAO = brandsDAO;
    }

    @Override
    public List<Brands> findAll() {
        return brandsDAO.findAll();
    }

    @Override
    public Brands findById(int theId) {
        return brandsDAO.findById(theId);
    }

    @Override
    public void update(Brands brands) {
        brandsDAO.update(brands);
    }

    @Override
    public void save(Brands brands) {
        brandsDAO.save(brands);
    }

    @Override
    public void delete(int theId) {
        brandsDAO.delete(theId);
    }
}
