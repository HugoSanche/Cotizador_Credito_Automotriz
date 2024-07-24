package com.cotizador.dao;

import com.cotizador.entity.Charges;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChargesDAOImp implements ChargesDAO{
    private EntityManager entityManager;

    @Autowired
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
    public Charges findByName(String nameCharge) {

        //get list results
        //create a query
        TypedQuery<Charges> theQuery=entityManager.createQuery("from Charges where name=:theData", Charges.class);

        //set query parameters
        theQuery.setParameter("theData",nameCharge);

        //retorna solamente 1
        return theQuery.getResultList().get(0);
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
