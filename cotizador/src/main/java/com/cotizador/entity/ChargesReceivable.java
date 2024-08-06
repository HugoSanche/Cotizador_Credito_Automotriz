package com.cotizador.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name ="chargesreceivable" )
public class ChargesReceivable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ChargesReceivableId")
    private int chargesReceivableId;

    @Column(name = "ChargesId")
    private int chargesId;
    @Column(name = "DueDate")
    private Date dueDate;
    @Column(name = "EnforceabilityDate")
    private Date enforceabilityDate;
    @Column(name = "ChargeAmount")
    private BigDecimal chargeAmount;

    @Column(name = "ChargeBalance")
    private BigDecimal chargeBalance;
    @Column(name = "VATAmount")
    private BigDecimal vATAmount;
    @Column(name = "VATBalance")
    private BigDecimal vATBalance;
    @Column(name = "LateInterestAmount")
    private BigDecimal lateInterestAmount;
    @Column(name = "LateInterestBalance")
    private BigDecimal lateInterestBalance;
    @Column(name = "LateInterestQtyOfDays")
    private BigDecimal lateInterestQtyOfDays;
    @Column(name = "VATAmountForLateInterest")
    private BigDecimal vATAmountForLateInterest;
    @Column(name = "VATBalanceForLateInterest")
    private BigDecimal vATBalanceForLateInterest;
    @Column(name = "Comments")
    private String comments;
    @Column(name = "LastUpdate")
    private Date lastUpdate;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "payment_charges",
            joinColumns = @JoinColumn(name = "ChargesReceivableId"),
            inverseJoinColumns = @JoinColumn(name="PaymentCalculatorId")
    )
    List<PaymentCalculator> paymentCalculators;

    @Column(name = "Register")
    private Date register;
    @Column(name = "Status")
    private String status;

    public ChargesReceivable(int chargesId, Date dueDate, Date enforceabilityDate, BigDecimal chargeAmount,
                             BigDecimal chargeBalance, BigDecimal vATAmount, BigDecimal vATBalance, BigDecimal lateInterestAmount,
                             BigDecimal lateInterestBalance, BigDecimal lateInterestQtyOfDays, BigDecimal vATAmountForLateInterest,
                             BigDecimal vATBalanceForLateInterest, String comments, Date lastUpdate, Date register,
                             String status) {
        this.chargesId=chargesId;
        this.dueDate = dueDate;
        this.enforceabilityDate = enforceabilityDate;
        this.chargeAmount = chargeAmount;
        this.chargeBalance = chargeBalance;
        this.vATAmount = vATAmount;
        this.vATBalance = vATBalance;
        this.lateInterestAmount = lateInterestAmount;
        this.lateInterestBalance = lateInterestBalance;
        this.lateInterestQtyOfDays = lateInterestQtyOfDays;
        this.vATAmountForLateInterest = vATAmountForLateInterest;
        this.vATBalanceForLateInterest = vATBalanceForLateInterest;
        this.comments = comments;
        this.lastUpdate = lastUpdate;
        this.register = register;
        this.status = status;
    }

    public int getChargesId() {
        return chargesId;
    }

    public void setChargesId(int chargesId) {
        this.chargesId = chargesId;
    }

    private void addPaymentCalculator(PaymentCalculator thePaymentCalculators){
        if (paymentCalculators==null){
            paymentCalculators=new ArrayList<>();
        }
        paymentCalculators.add(thePaymentCalculators);

    }

    public int getChargesReceivableId() {
        return chargesReceivableId;
    }

    public void setChargesReceivableId(int chargesReceivableId) {
        this.chargesReceivableId = chargesReceivableId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getEnforceabilityDate() {
        return enforceabilityDate;
    }

    public void setEnforceabilityDate(Date enforceabilityDate) {
        this.enforceabilityDate = enforceabilityDate;
    }

    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public BigDecimal getChargeBalance() {
        return chargeBalance;
    }

    public void setChargeBalance(BigDecimal chargeBalance) {
        this.chargeBalance = chargeBalance;
    }

    public BigDecimal getvATAmount() {
        return vATAmount;
    }

    public void setvATAmount(BigDecimal vATAmount) {
        this.vATAmount = vATAmount;
    }

    public BigDecimal getvATBalance() {
        return vATBalance;
    }

    public void setvATBalance(BigDecimal vATBalance) {
        this.vATBalance = vATBalance;
    }

    public BigDecimal getLateInterestAmount() {
        return lateInterestAmount;
    }

    public void setLateInterestAmount(BigDecimal lateInterestAmount) {
        this.lateInterestAmount = lateInterestAmount;
    }

    public BigDecimal getLateInterestBalance() {
        return lateInterestBalance;
    }

    public void setLateInterestBalance(BigDecimal lateInterestBalance) {
        this.lateInterestBalance = lateInterestBalance;
    }

    public BigDecimal getLateInterestQtyOfDays() {
        return lateInterestQtyOfDays;
    }

    public void setLateInterestQtyOfDays(BigDecimal lateInterestQtyOfDays) {
        this.lateInterestQtyOfDays = lateInterestQtyOfDays;
    }

    public BigDecimal getvATAmountForLateInterest() {
        return vATAmountForLateInterest;
    }

    public void setvATAmountForLateInterest(BigDecimal vATAmountForLateInterest) {
        this.vATAmountForLateInterest = vATAmountForLateInterest;
    }

    public BigDecimal getvATBalanceForLateInterest() {
        return vATBalanceForLateInterest;
    }

    public void setvATBalanceForLateInterest(BigDecimal vATBalanceForLateInterest) {
        this.vATBalanceForLateInterest = vATBalanceForLateInterest;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //Suma el cargo y su iva
    public  BigDecimal getSumAmountAndVATAmount(){
        return chargeAmount.add(vATAmount);
    }
//    public String getCharge(){
//        ChargeService chargeService=;
//        Charges chargeInterestPeriod = chargeService.findByName("Intereses del Periodo");
//
//    }

    @Override
    public String toString() {
        return "ChargesReceivable{" +
                "chargesReceivableId=" + chargesReceivableId +
                ", dueDate=" + dueDate +
                ", enforceabilityDate=" + enforceabilityDate +
                ", chargeAmount=" + chargeAmount +
                ", chargeBalance=" + chargeBalance +
                ", vATAmount=" + vATAmount +
                ", vATBalance=" + vATBalance +
                ", lateInterestAmount=" + lateInterestAmount +
                ", lateInterestBalance=" + lateInterestBalance +
                ", lateInterestQtyOfDays=" + lateInterestQtyOfDays +
                ", vATAmountForLateInterest=" + vATAmountForLateInterest +
                ", vATBalanceForLateInterest=" + vATBalanceForLateInterest +
                ", comments='" + comments + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", register=" + register +
                ", status='" + status + '\'' +
                '}';
    }
}
