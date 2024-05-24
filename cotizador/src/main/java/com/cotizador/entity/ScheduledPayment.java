package com.cotizador.entity;

import jakarta.persistence.*;

//Tabla de amortizacion
@Entity
@Table(name = "scheduledpayments")
public class ScheduledPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = scheduledPaymentId)

}
