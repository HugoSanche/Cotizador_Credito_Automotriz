package com.cotizador.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "paymentcalculator")
public class Paymentcalculator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentCalculatorId")
    private int paymentCalculatorId;
    @Column(name = "Date")
    private Date fechaCotizacion;
    @Column(name = "YearVehicle")
    private Date yearVehicle;
    @Column(name = "VehiclePrice")
    private double vehiclePrice;
    @Column(name = "DownPayment")
    private double downPayment;
    @Column(name = "PersonId")
    private int personId;
    @Column(name = "BrandId")
    private int brandId;
    @Column(name = "ModelId")
    private int modelId;

    @Column(name = "LoanTerm")
    private int loanTerm;
    @Column(name = "Version")
    private int version;

}
















