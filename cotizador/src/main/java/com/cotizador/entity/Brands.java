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

    public Brands(int brandId, String name, String status) {
        this.brandId = brandId;
        this.name = name;
        this.status = status;
    }

    public Brands() {
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Brands{" +
                "brandId=" + brandId +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
