package com.cotizador.entity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class PaymentDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentDayId")
    private int paymentDayId;
    @Column(name = "PaymentDay")
    private int paymentDay;
    @Column(name = "Execute")
    private boolean execute;
    @Column(name = "Status")
    private String status;
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    @Column(name = "Register")
    private Date register;

    public PaymentDay(int paymentDayId, int paymentDay, boolean execute, String status, Date lastUpdate, Date register) {
        this.paymentDayId = paymentDayId;
        this.paymentDay = paymentDay;
        this.execute = execute;
        this.status = status;
        this.lastUpdate = lastUpdate;
        this.register = register;
    }

    public int getPaymentDayId() {
        return paymentDayId;
    }

    public void setPaymentDayId(int paymentDayId) {
        this.paymentDayId = paymentDayId;
    }

    public int getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(int paymentDay) {
        this.paymentDay = paymentDay;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
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
}




