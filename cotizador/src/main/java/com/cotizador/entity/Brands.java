package com.cotizador.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "brands")
public class Brands {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BrandId")
    private int brandId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Status")
    private String status;


}
