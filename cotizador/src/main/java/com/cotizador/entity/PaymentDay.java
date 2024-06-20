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

}




