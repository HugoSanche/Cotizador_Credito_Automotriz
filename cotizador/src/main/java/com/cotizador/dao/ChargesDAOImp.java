package com.cotizador.dao;

import com.cotizador.entity.Charges;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChargesDAOImp implements ChargesDAO{
    EntityManager entityManager;

    public ChargesDAOImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Charges> findAll() {
        TypedQuery<Charges> charges=entityManager.createQuery("from Charges", Charges.class);
        return charges.getResultList();
    }

    @Override
    public Charges findById(int theId) {
        Charges charges=entityManager.find(Charges.class,theId);
        return charges;
    }

    @Override
    public List<Charges> findByName(String nameCharge) {
        TypedQuery<Charges> charges=entityManager.createQuery("from charges where name="+nameCharge, Charges.class);
        return charges.getResultList();
    }

    @Override
    public void update(Charges charges) {
        entityManager.merge(charges);

    }

    @Override
    public void save(Charges charges) {
        entityManager.persist(charges);
    }

    @Override
    public void delete(int theId) {
        Charges charges=entityManager.find(Charges.class,theId);
        entityManager.remove(charges);
    }
}
