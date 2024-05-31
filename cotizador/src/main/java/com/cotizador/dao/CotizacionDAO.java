package com.cotizador.dao;

import com.cotizador.entity.Cotizacion;

import java.util.List;

public interface CotizacionDAO {
    List<Cotizacion> findAll();
    Cotizacion findById(int id);
    void save(Cotizacion cotizacion);
    void update(Cotizacion cotizacion);
    void delete(int id);
}
