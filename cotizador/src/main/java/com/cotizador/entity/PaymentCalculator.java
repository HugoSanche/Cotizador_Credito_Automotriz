package com.cotizador.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "paymentcalculator")
public class PaymentCalculator  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentCalculatorId")
    private int paymentCalculatorId;
    @Column(name = "Date")
    private Date fechaCotizacion;
    @Column(name = "YearVehicle")
    @NotNull(message = "is required")
    private int yearVehicle;

    @Column(name = "VehiclePrice")
    @NotNull(message = "is required")
    private BigDecimal vehiclePrice;

    @Column(name = "DownPayment")
    @NotNull(message = "is required")
    private BigDecimal downPayment;

    @Column(name = "PersonId")
    @NotNull
    private int personId;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name ="personId" )
    private Individual individual;

    @Column(name = "BrandId")
    @NotNull
    private int brandId;
    @NotNull
    @Column(name = "ModelId")
    private int modelId;

    @Column(name = "LoanTerm")
    @NotNull
    private int loanTerm;

    @Column(name = "RateId")
    private int rateId;

    @Column(name = "RateValue")
    private double rateValue;

    @Column(name = "Version")
    private int version;


    public PaymentCalculator() {
    }

    public PaymentCalculator(int paymentCalculatorId, Date fechaCotizacion, int yearVehicle, BigDecimal vehiclePrice,
                             BigDecimal downPayment, int personId,  int brandId, int modelId, int loanTerm, int rateId,
                             double rateValue, int version) {
        this.paymentCalculatorId = paymentCalculatorId;
        this.fechaCotizacion = fechaCotizacion;
        this.yearVehicle = yearVehicle;
        this.vehiclePrice = vehiclePrice;
        this.downPayment = downPayment;
        this.personId=personId;
        this.brandId = brandId;
        this.modelId = modelId;
        this.loanTerm = loanTerm;
        this.rateId = rateId;
        this.rateValue = rateValue;
        this.version = version;
    }


    public PaymentCalculator(Date fechaCotizacion, int yearVehicle, BigDecimal vehiclePrice,
                             BigDecimal downPayment,  int personId, int brandId, int modelId, int loanTerm, int rateId,
                             double rateValue, int version) {
        this.paymentCalculatorId = paymentCalculatorId;
        this.fechaCotizacion = fechaCotizacion;
        this.yearVehicle = yearVehicle;
        this.vehiclePrice = vehiclePrice;
        this.downPayment = downPayment;
        this.personId=personId;
        this.brandId = brandId;
        this.modelId = modelId;
        this.loanTerm = loanTerm;
        this.rateId = rateId;
        this.rateValue = rateValue;
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

                ", individual=" + individual +
                ", brandId=" + brandId +
                ", modelId=" + modelId +
                ", loanTerm=" + loanTerm +
                ", version=" + version +
                '}';
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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

    public BigDecimal getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(BigDecimal vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }

    public BigDecimal getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(BigDecimal downPayment) {
        this.downPayment = downPayment;
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

    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    public double getRateValue() {
        return rateValue;
    }

    public void setRateValue(double rateValue) {
        this.rateValue = rateValue;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public BigDecimal calculateAmountCredit(){
        BigDecimal interestAmount=vehiclePrice.subtract(downPayment);

        return interestAmount;

    }

    public BigDecimal getAmountFinanced(){
        return vehiclePrice.subtract(downPayment);
    }


}
















