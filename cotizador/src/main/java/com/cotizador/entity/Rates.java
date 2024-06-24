package com.cotizador.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rates")
public class Rates {

    @Column(name = "RateId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rateId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @Column(name = "Status")
    private String status;

    public Rates(int rateId, String name, String description, String status) {
        this.rateId = rateId;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
