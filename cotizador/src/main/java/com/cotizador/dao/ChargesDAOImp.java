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
    public List<Charges> findByName(String nameCharge) {

//        //create a query
//        TypedQuery<Student> theQuery=entityManager.createQuery("from Student where secondName=:theData",Student.class);
//
//        //set query parameters
//        theQuery.setParameter("theData",secondName);

        System.out.println("name charge "+nameCharge);
        //get list results
        //create a query
        TypedQuery<Charges> theQuery=entityManager.createQuery("from Charges where name=:theData", Charges.class);

        //set query parameters
        theQuery.setParameter("theData",nameCharge);

        System.out.println("The query "+theQuery.getResultList());

        return theQuery.getResultList();
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
