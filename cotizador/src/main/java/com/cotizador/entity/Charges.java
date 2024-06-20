package com.cotizador.entity;

import jakarta.persistence.*;

import java.util.Date;

@Table
@Entity
public class Charges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ChargesId")
    private int chargesId;
    @Column(name = "Name")
    private String name;
    @Column(name = "CalculationMethod")
    private String calculationMethod;
    @Column(name = "CalculationValue")
    private String calculationValue;
    @Column(name = "TransactionType")
    private String transactionType;
    @Column(name = "ChargeType")
    private String chargeType;
    @Column(name = "Status")
    private String status;
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    @Column(name = "Register")
    private Date register;

}




