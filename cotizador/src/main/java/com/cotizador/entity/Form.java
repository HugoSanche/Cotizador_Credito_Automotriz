package com.cotizador.entity;

public class Form {
    private Individual theIndividual;
    private PaymentCalculator thePaymentCalculator;

    public Form(Individual theIndividual, PaymentCalculator thePaymentCalculator) {
        this.theIndividual = theIndividual;
        this.thePaymentCalculator = thePaymentCalculator;
    }

    public Form() {
    }

    public Individual getTheIndividual() {
        return theIndividual;
    }

    public void setTheIndividual(Individual theIndividual) {
        this.theIndividual = theIndividual;
    }

    public PaymentCalculator getThePaymentCalculator() {
        return thePaymentCalculator;
    }

    public void setThePaymentCalculator(PaymentCalculator thePaymentCalculator) {
        this.thePaymentCalculator = thePaymentCalculator;
    }

}
