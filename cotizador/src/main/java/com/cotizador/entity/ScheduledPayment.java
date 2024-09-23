package com.cotizador.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

//Tabla de amortizacion
@Entity
@Table(name = "scheduledpayments")
public class ScheduledPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ScheduledPaymentId")
    private BigInteger scheduledPaymentId;

    @Column(name = "PaymentCalculatorId")
    private int paymentCalculatorId;
    @Column(name = "PaymentNumber")
    private int paymentNumber;
    @Column(name = "CapitalAmount")
    private BigDecimal capitalAmount;
    @Column(name = "CapitalQuantityOfDays")
    private int capitalQuantityOfDays;
    @Column(name = "CapitalBalance")
    private BigDecimal capitalBalance;
    @Column(name = "InterestAmount")
    private BigDecimal interestAmount;
    @Column(name = "InterestQuantityOfDays")
    private BigDecimal interestQuantityOfDays;
    @Column(name = "InterestBalance")
    private BigDecimal interestBalance;
    @Column(name = "VATAmountForCapital")
    private BigDecimal vATAmountForCapital;

    @Column(name = "VATBalanceForCapital")
    private BigDecimal vATBalanceForCapital;
    @Column(name = "PaymentToDeferAmount")
    private BigDecimal paymentToDeferAmount;
    @Column(name = "PaymentToDeferBalance")
    private BigDecimal paymentToDeferBalance;
    @Column(name = "VATAmountForPaymentToDefer")
    private BigDecimal vATAmountForPaymentToDefer;
    @Column(name = "VATBalanceForPaymentToDefer")
    private BigDecimal vATBalanceForPaymentToDefer;
    @Column(name = "TotalPaymentAmount")
    private BigDecimal totalPaymentAmount;
    @Column(name = "DueDate")
    private Date dueDate;
    @Column(name = "Status")
    private String status;
    @Column(name = "ContractInitialBalance")
    private BigDecimal contractInitialBalance;
    @Column(name = "ContractFinalBalance")
    private BigDecimal contractFinalBalance;
    @Column(name = "VATAmountForInterest")
    private BigDecimal vATAmountForInterest;
    @Column(name = "VATBalanceForInterest")
    private BigDecimal vATBalanceForInterest;
    @Column(name = "CancellationDate")
    private Date cancellationDate;

    public ScheduledPayment() {
    }

    public ScheduledPayment(int paymentCalculatorId, int paymentNumber, BigDecimal capitalAmount,
                            int capitalQuantityOfDays, BigDecimal capitalBalance, BigDecimal interestAmount,
                            BigDecimal interestQuantityOfDays, BigDecimal interestBalance, BigDecimal vATAmountForCapital,
                            BigDecimal vATBalanceForCapital, BigDecimal paymentToDeferAmount, BigDecimal paymentToDeferBalance,
                            BigDecimal vATAmountForPaymentToDefer, BigDecimal vATBalanceForPaymentToDefer,
                            BigDecimal totalPaymentAmount, Date dueDate, String status, BigDecimal contractInitialBalance,
                            BigDecimal contractFinalBalance, BigDecimal vATAmountForInterest, BigDecimal vATBalanceForInterest,
                            Date cancellationDate) {
    this.paymentCalculatorId=paymentCalculatorId;
        this.paymentNumber = paymentNumber;
        this.capitalAmount = capitalAmount;
        this.capitalQuantityOfDays = capitalQuantityOfDays;
        this.capitalBalance = capitalBalance;
        this.interestAmount = interestAmount;
        this.interestQuantityOfDays = interestQuantityOfDays;
        this.interestBalance = interestBalance;
        this.vATAmountForCapital = vATAmountForCapital;
        this.vATBalanceForCapital = vATBalanceForCapital;
        this.paymentToDeferAmount = paymentToDeferAmount;
        this.paymentToDeferBalance = paymentToDeferBalance;
        this.vATAmountForPaymentToDefer = vATAmountForPaymentToDefer;
        this.vATBalanceForPaymentToDefer = vATBalanceForPaymentToDefer;
        this.totalPaymentAmount = totalPaymentAmount;
        this.dueDate = dueDate;
        this.status = status;
        this.contractInitialBalance = contractInitialBalance;
        this.contractFinalBalance = contractFinalBalance;
        this.vATAmountForInterest = vATAmountForInterest;
        this.vATBalanceForInterest = vATBalanceForInterest;
        this.cancellationDate = cancellationDate;
    }

    public int getPaymentCalculatorId() {
        return paymentCalculatorId;
    }

    public void setPaymentCalculatorId(int paymentCalculatorId) {
        this.paymentCalculatorId = paymentCalculatorId;
    }

    public BigInteger getScheduledPaymentId() {
        return scheduledPaymentId;
    }

    public void setScheduledPaymentId(BigInteger scheduledPaymentId) {
        this.scheduledPaymentId = scheduledPaymentId;
    }



    public int getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(int paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public BigDecimal getCapitalAmount() {
        return capitalAmount;
    }

    public void setCapitalAmount(BigDecimal capitalAmount) {
        this.capitalAmount = capitalAmount;
    }

    public int getCapitalQuantityOfDays() {
        return capitalQuantityOfDays;
    }

    public void setCapitalQuantityOfDays(int capitalQuantityOfDays) {
        this.capitalQuantityOfDays = capitalQuantityOfDays;
    }

    public BigDecimal getCapitalBalance() {
        return capitalBalance;
    }

    public void setCapitalBalance(BigDecimal capitalBalance) {
        this.capitalBalance = capitalBalance;
    }

    public BigDecimal getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
    }

    public BigDecimal getInterestQuantityOfDays() {
        return interestQuantityOfDays;
    }

    public void setInterestQuantityOfDays(BigDecimal interestQuantityOfDays) {
        this.interestQuantityOfDays = interestQuantityOfDays;
    }

    public BigDecimal getInterestBalance() {
        return interestBalance;
    }

    public void setInterestBalance(BigDecimal interestBalance) {
        this.interestBalance = interestBalance;
    }

    public BigDecimal getvATAmountForCapital() {
        return vATAmountForCapital;
    }

    public void setvATAmountForCapital(BigDecimal vATAmountForCapital) {
        this.vATAmountForCapital = vATAmountForCapital;
    }

    public BigDecimal getvATBalanceForCapital() {
        return vATBalanceForCapital;
    }

    public void setvATBalanceForCapital(BigDecimal vATBalanceForCapital) {
        this.vATBalanceForCapital = vATBalanceForCapital;
    }

    public BigDecimal getPaymentToDeferAmount() {
        return paymentToDeferAmount;
    }

    public void setPaymentToDeferAmount(BigDecimal paymentToDeferAmount) {
        this.paymentToDeferAmount = paymentToDeferAmount;
    }

    public BigDecimal getPaymentToDeferBalance() {
        return paymentToDeferBalance;
    }

    public void setPaymentToDeferBalance(BigDecimal paymentToDeferBalance) {
        this.paymentToDeferBalance = paymentToDeferBalance;
    }

    public BigDecimal getvATAmountForPaymentToDefer() {
        return vATAmountForPaymentToDefer;
    }

    public void setvATAmountForPaymentToDefer(BigDecimal vATAmountForPaymentToDefer) {
        this.vATAmountForPaymentToDefer = vATAmountForPaymentToDefer;
    }

    public BigDecimal getvATBalanceForPaymentToDefer() {
        return vATBalanceForPaymentToDefer;
    }

    public void setvATBalanceForPaymentToDefer(BigDecimal vATBalanceForPaymentToDefer) {
        this.vATBalanceForPaymentToDefer = vATBalanceForPaymentToDefer;
    }

    public BigDecimal getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(BigDecimal totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getContractInitialBalance() {
        return contractInitialBalance;
    }

    public void setContractInitialBalance(BigDecimal contractInitialBalance) {
        this.contractInitialBalance = contractInitialBalance;
    }

    public BigDecimal getContractFinalBalance() {
        return contractFinalBalance;
    }

    public void setContractFinalBalance(BigDecimal contractFinalBalance) {
        this.contractFinalBalance = contractFinalBalance;
    }

    public BigDecimal getvATAmountForInterest() {
        return vATAmountForInterest;
    }

    public void setvATAmountForInterest(BigDecimal vATAmountForInterest) {
        this.vATAmountForInterest = vATAmountForInterest;
    }

    public BigDecimal getvATBalanceForInterest() {
        return vATBalanceForInterest;
    }

    public void setvATBalanceForInterest(BigDecimal vATBalanceForInterest) {
        this.vATBalanceForInterest = vATBalanceForInterest;
    }

    public Date getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Date cancellationDate) {
        this.cancellationDate = cancellationDate;
    }
}
