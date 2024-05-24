package com.cotizador.entity;

import jakarta.persistence.*;

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

    @Column(name = "contrato")
    private int contrato;

    @Column(name = "anexo")
    private int anexo;

    @Column(name = "PaymentNumber")
    private int paymentNumber;
    @Column(name = "CapitalAmount")
    private double capitalAmount;
    @Column(name = "CapitalQuantityOfDays")
    private int capitalQuantityOfDays;
    @Column(name = "CapitalBalance")
    private int capitalBalance;
    @Column(name = "InterestAmount")
    private double interestAmount;
    @Column(name = "InterestQuantityOfDays")
    private int interestQuantityOfDays;
    @Column(name = "InterestBalance")
    private int interestBalance;
    @Column(name = "VATAmountForCapital")
    private double vATAmountForCapital;

    @Column(name = "VATBalanceForCapital")
    private int vATBalanceForCapital;
    @Column(name = "PaymentToDeferAmount")
    private int paymentToDeferAmount;
    @Column(name = "PaymentToDeferBalance")
    private int paymentToDeferBalance;
    @Column(name = "VATAmountForPaymentToDefer")
    private int vATAmountForPaymentToDefer;
    @Column(name = "VATBalanceForPaymentToDefer")
    private int vATBalanceForPaymentToDefer;
    @Column(name = "TotalPaymentAmount")
    private double totalPaymentAmount;
    @Column(name = "DueDate")
    private Date dueDate;
    @Column(name = "Status")
    private String status;
    @Column(name = "ContractInitialBalance")
    private double contractInitialBalance;
    @Column(name = "ContractFinalBalance")
    private double contractFinalBalance;
    @Column(name = "VATAmountForInterest")
    private double vATAmountForInterest;
    @Column(name = "VATBalanceForInterest")
    private double vATBalanceForInterest;
    @Column(name = "CancellationDate")
    private Date cancellationDate;
}
