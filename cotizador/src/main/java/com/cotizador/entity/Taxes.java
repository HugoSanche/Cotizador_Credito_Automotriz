package com.cotizador.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "taxes")
public class Taxes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ImpuestoId")
    private int impuestoId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Value")
    private Double Value;
    @Column(name = "Description")
    private String Description;
    @Column(name = "Status")
    private String Status;

    public Taxes(int impuestoId, String name, Double value, String description, String status) {
        this.impuestoId = impuestoId;
        this.name = name;
        Value = value;
        Description = description;
        Status = status;
    }

    public Taxes() {
    }

    public int getImpuestoId() {
        return impuestoId;
    }

    public void setImpuestoId(int impuestoId) {
        this.impuestoId = impuestoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return Value;
    }

    public void setValue(Double value) {
        Value = value;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Taxes{" +
                "impuestoId=" + impuestoId +
                ", name='" + name + '\'' +
                ", Value=" + Value +
                ", Description='" + Description + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}
