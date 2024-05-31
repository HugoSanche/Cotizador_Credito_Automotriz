package com.cotizador.dao;

import java.util.List;

public interface Cotizacion {
    List<Cotizacion> findAll();
    Cotizacion findById(int id);
    void save(Cotizacion cotizacion);
    void update(Cotizacion cotizacion);
    void delete(int id);
}
