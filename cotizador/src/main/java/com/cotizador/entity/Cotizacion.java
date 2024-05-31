package com.cotizador.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "cotizacion")
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CotizacionId")
    private int cotizacionId;
    @Column(name = "FechaCotizacion")
    private Date fechaCotizacion;
    @Column(name = "ImporteFinanciar")
    private double importeFinanciar;
    @Column(name = "Enganche")
    private double enganche;
    @Column(name = "PersonId")
    private int personId;
    @Column(name = "BrandId")
    private int brandId;
    @Column(name = "ModelId")
    private int modelId;

}
