package com.cotizador.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "models")
public class Models {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ModelId")
    private int modelId;
    @Column(name = "BrandId")
    private int brandId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @Column(name = "Status")
    private String status;
}
