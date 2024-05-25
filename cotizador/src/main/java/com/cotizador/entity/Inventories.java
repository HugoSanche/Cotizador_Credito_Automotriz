package com.cotizador.entity;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "inventories")
public class Inventories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InventoryId")
   private int inventoryId;
    @Column(name = "IsInventory")
    private int isInventory;
    @Column(name = "ShortDescription")
    private String shortDescription;
    @Column(name = "OriginalProviderName")
    private String originalProviderName;
    @Column(name = "Contract")
    private int contract;
    @Column(name = "Lease")
    private int lease;
    @Column(name = "ContractLeaseId")
    private int contractLeaseId;
    @Column(name = "TotalFinancingAmount")
    private double totalFinancingAmount;
    @Column(name = "Status")
    private String status;
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    @Column(name = "Register")
    private String register;
    @Column(name = "ProductType")
    private String productType;
    @Column(name = "MarketValue")
    private double marketValue;
    @Column(name = "CurrencyId")
    private int currencyId;
    @Column(name = "ExternalReferenceInsurance")
    private String  externalReferenceInsurance;
    @Column(name = "SerialNumber")
    private String  serialNumber;
    @Column(name = "MotorNumber")
    private String  motorNumber;
    @Column(name = "Year")
    private int year;
    @Column(name = "BrandId")
    private int brandId;
}
