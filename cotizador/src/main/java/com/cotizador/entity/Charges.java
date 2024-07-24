package com.cotizador.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Entity
@Table(name = "charges")
public class Charges implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ChargesId")
    private int chargesId;
    @Column(name = "Name")
    private String name;
    @Column(name = "CalculationMethod")
    private String calculationMethod;
    @Column(name = "CalculationValue")
    private BigDecimal calculationValue;
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

    public Charges() {
    }

    public Charges(int chargesId, String name, String calculationMethod, BigDecimal calculationValue, String transactionType,
                   String chargeType, String status, Date lastUpdate, Date register) {
        this.chargesId = chargesId;
        this.name = name;
        this.calculationMethod = calculationMethod;
        this.calculationValue = calculationValue;
        this.transactionType = transactionType;
        this.chargeType = chargeType;
        this.status = status;
        this.lastUpdate = lastUpdate;
        this.register = register;
    }

    public int getChargesId() {
        return chargesId;
    }

    public void setChargesId(int chargesId) {
        this.chargesId = chargesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalculationMethod() {
        return calculationMethod;
    }

    public void setCalculationMethod(String calculationMethod) {
        this.calculationMethod = calculationMethod;
    }

    public BigDecimal getCalculationValue() {
        return calculationValue;
    }

    public void setCalculationValue(BigDecimal calculationValue) {
        this.calculationValue = calculationValue;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Date getRegister() {
        return register;
    }

    public void setRegister(Date register) {
        this.register = register;
    }

    //calcula el monto de la comision por apertura
    public BigDecimal getComisionXApertura(BigDecimal amountOfCredit){
        BigDecimal value = new BigDecimal(100);

        return amountOfCredit.multiply(getCalculationValue()).divide(value, RoundingMode.HALF_UP);
    }
    @Override
    public String toString() {
        return "Charges{" +
                "chargesId=" + chargesId +
                ", name='" + name + '\'' +
                ", calculationMethod='" + calculationMethod + '\'' +
                ", calculationValue=" + calculationValue +
                ", transactionType='" + transactionType + '\'' +
                ", chargeType='" + chargeType + '\'' +
                ", status='" + status + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", register=" + register +
                '}';
    }
}




