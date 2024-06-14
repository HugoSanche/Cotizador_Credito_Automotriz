package com.cotizador.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "paymentcalculator")
public class PaymentCalculator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentCalculatorId")
    private int paymentCalculatorId;
    @Column(name = "Date")
    private Date fechaCotizacion;
    @Column(name = "YearVehicle")
    private int yearVehicle;
    @Column(name = "VehiclePrice")
    private double vehiclePrice;
    @Column(name = "DownPayment")
    private double downPayment;


    @Column(name = "PersonId")
    private int personId;

    @ManyToOne
    @JoinColumn(name ="personId" )
    private Individual individual;

    @Column(name = "BrandId")
    private int brandId;
    @Column(name = "ModelId")
    private int modelId;

    @Column(name = "LoanTerm")
    private int loanTerm;
    @Column(name = "Version")
    private int version;

    public PaymentCalculator(  int yearVehicle, double vehiclePrice, double downPayment, int loanTerm) {
        this.yearVehicle = yearVehicle;
        this.vehiclePrice = vehiclePrice;
        this.downPayment = downPayment;
        this.loanTerm = loanTerm;
    }

    public PaymentCalculator(int paymentCalculatorId, Date fechaCotizacion, int yearVehicle, double vehiclePrice,
                             double downPayment, int personId, Individual individual, int brandId, int modelId,
                             int loanTerm, int version) {
        this.paymentCalculatorId = paymentCalculatorId;
        this.fechaCotizacion = fechaCotizacion;
        this.yearVehicle = yearVehicle;
        this.vehiclePrice = vehiclePrice;
        this.downPayment = downPayment;
        this.personId = personId;
        this.individual = individual;
        this.brandId = brandId;
        this.modelId = modelId;
        this.loanTerm = loanTerm;
        this.version = version;
    }


    public PaymentCalculator() {
    }

    public int getPaymentCalculatorId() {
        return paymentCalculatorId;
    }

    public void setPaymentCalculatorId(int paymentCalculatorId) {
        this.paymentCalculatorId = paymentCalculatorId;
    }

    public Date getFechaCotizacion() {
        return fechaCotizacion;
    }

    public void setFechaCotizacion(Date fechaCotizacion) {
        this.fechaCotizacion = fechaCotizacion;
    }

    public int getYearVehicle() {
        return yearVehicle;
    }

    public void setYearVehicle(int yearVehicle) {
        this.yearVehicle = yearVehicle;
    }

    public double getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(double vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public Individual getIndividual() {
        return individual;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "PaymentCalculator{" +
                "paymentCalculatorId=" + paymentCalculatorId +
                ", fechaCotizacion=" + fechaCotizacion +
                ", yearVehicle=" + yearVehicle +
                ", vehiclePrice=" + vehiclePrice +
                ", downPayment=" + downPayment +
                ", personId=" + personId +
                ", individual=" + individual +
                ", brandId=" + brandId +
                ", modelId=" + modelId +
                ", loanTerm=" + loanTerm +
                ", version=" + version +
                '}';
    }
}















