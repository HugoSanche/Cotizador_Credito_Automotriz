package com.cotizador.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="individuals")
public class Individual implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PersonId")
    private int personId;

    @NotNull(message = "is required")
    @Size (min =3, message = "is required")
    @Column(name="FirstName")
    private String firstName;

    @Column(name = "MiddleName")
    private String middleName;

    @NotNull(message = "is required")
    @Size (min =3, message = "is required")
    @Column(name="FirstLastName")
    private String firstLastName;

    @NotNull(message = "is required")
    @Size (min =3, message = "is required")
    @Column(name="SecondLastName")
    private String secondLastName;


    @NotNull(message = "is required")
    @NotEmpty
    @Email(message = "Format error")
    @Column(name="Email")
    private String email;

    @NotNull
    @NotEmpty
    @Pattern(regexp="(^$|[0-9]{10})")
    @Size(min=10,max=10,message = "Must be equal to 10")
    @Column(name="PhoneNumber")
    private String phoneNumber;
    @Column(name="MaritalStatus")
    private String maritalStatus;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "BirthDate", nullable = false)
    private Date birthDate;

    @Column(name="Nacionality")
    private int nationality;
    @Column(name="NumberOfDependents")
    private int numberOfDependents;

    @Column(name="DivorceLegalAgreement")
    private String divorceLegalAgreement;

    @Column(name="DwellingType")
    private String dwellingType;

    @Column(name="IsDwellingFreeOfEncumbrance")
    private int isDwellingFreeOfEncumbrance;

    @Column(name="Occupation")
    private String occupation;

    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy, timezone = UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="StartDateInCurrentJob")
    private Date startDateInCurrentJob;

    @Column(name="CurrentPositionName")
    private String currentPositionName;

    @NumberFormat(style= NumberFormat.Style.CURRENCY)
    @DecimalMin("0.01")
    @Column(name="CurrentMonthlyIncome")
    private BigDecimal currentMonthlyIncome;

    @Column(name="DoesBizActivities")
    private int doesBizActivities;

    @Column(name="HiringType")
    private String hiringType;
    @Column(name="ExternalEmployeeNumber")
    private String externalEmployeeNumber;
    @Column(name="Gender")
    private String gender;

//    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
//    @JoinColumn(name="CurrencyId")
//    private Currenci currency;

    @Column(name="CurrencyId")
    private Integer currencyId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="personId")
    private List<PaymentCalculator> paymentCalculadors;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "LastUpdate")
    private Date lastUpdate;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Register")
    private Date register;


    @PreUpdate
    protected void prePersist() {
        //if (this.birthDate == null) birthDate = new Date();
        lastUpdate = new Date();
        if(this.register==null) register=new Date();
        System.out.println("Last Updated "+lastUpdate);
    }


    @PostPersist
    protected  void postPersist(){
        lastUpdate=new Date();
        register=new Date();
    }

    public Individual(int personId, String firstName, String middleName, String firstLastName, String secondLastName, String email,
                      String phoneNumber, String maritalStatus, Date birthDate, int nationality, int numberOfDependents,
                      String divorceLegalAgreement, String dwellingType, int isDwellingFreeOfEncumbrance, String occupation,
                      Date startDateInCurrentJob, String currentPositionName, BigDecimal currentMonthlyIncome, int doesBizActivities,
                      String hiringType, String externalEmployeeNumber,
                      String gender, Integer currencyId,  Date lastUpdate, Date register) {
        this.personId = personId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.maritalStatus = maritalStatus;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.numberOfDependents = numberOfDependents;
        this.divorceLegalAgreement = divorceLegalAgreement;
        this.dwellingType = dwellingType;
        this.isDwellingFreeOfEncumbrance = isDwellingFreeOfEncumbrance;
        this.occupation = occupation;
        this.startDateInCurrentJob = startDateInCurrentJob;
        this.currentPositionName = currentPositionName;
        this.currentMonthlyIncome = currentMonthlyIncome;
        this.doesBizActivities = doesBizActivities;
        this.hiringType = hiringType;
        this.externalEmployeeNumber = externalEmployeeNumber;
        this.gender = gender;
        this.currencyId = currencyId;

        this.lastUpdate = lastUpdate;
        this.register = register;
    }

    public Individual() {
    }

    public List<PaymentCalculator> getPaymentCalculadors() {
        return paymentCalculadors;
    }

    public void setPaymentCalculadors(List<PaymentCalculator> paymentCalculadors) {
        this.paymentCalculadors = paymentCalculadors;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNationality(int nationality) {
        this.nationality = nationality;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getNationality() {
        return nationality;
    }

    public void setNationality(Integer nationality) {
        this.nationality = nationality;
    }

    public int getNumberOfDependents() {
        return numberOfDependents;
    }

    public void setNumberOfDependents(int numberOfDependents) {
        this.numberOfDependents = numberOfDependents;
    }

    public String getDivorceLegalAgreement() {
        return divorceLegalAgreement;
    }

    public void setDivorceLegalAgreement(String divorceLegalAgreement) {
        this.divorceLegalAgreement = divorceLegalAgreement;
    }

    public String getDwellingType() {
        return dwellingType;
    }

    public void setDwellingType(String dwellingType) {
        this.dwellingType = dwellingType;
    }

    public int getIsDwellingFreeOfEncumbrance() {
        return isDwellingFreeOfEncumbrance;
    }

    public void setIsDwellingFreeOfEncumbrance(int isDwellingFreeOfEncumbrance) {
        this.isDwellingFreeOfEncumbrance = isDwellingFreeOfEncumbrance;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Date getStartDateInCurrentJob() {
        return startDateInCurrentJob;
    }

    public void setStartDateInCurrentJob(Date startDateInCurrentJob) {
        this.startDateInCurrentJob = startDateInCurrentJob;
    }

    public String getCurrentPositionName() {
        return currentPositionName;
    }

    public void setCurrentPositionName(String currentPositionName) {
        this.currentPositionName = currentPositionName;
    }

    public BigDecimal getCurrentMonthlyIncome() {
        return currentMonthlyIncome;
    }

    public void setCurrentMonthlyIncome(BigDecimal currentMonthlyIncome) {
        this.currentMonthlyIncome = currentMonthlyIncome;
    }

    public int getDoesBizActivities() {
        return doesBizActivities;
    }

    public void setDoesBizActivities(int doesBizActivities) {
        this.doesBizActivities = doesBizActivities;
    }

    public String getHiringType() {
        return hiringType;
    }

    public void setHiringType(String hiringType) {
        this.hiringType = hiringType;
    }

    public String getExternalEmployeeNumber() {
        return externalEmployeeNumber;
    }

    public void setExternalEmployeeNumber(String externalEmployeeNumber) {
        this.externalEmployeeNumber = externalEmployeeNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    @Override
    public String toString() {
        return "Individual{" +
                "person=" + personId +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", firstLastName='" + firstLastName + '\'' +
                ", secondLastName='" + secondLastName + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +

                //", birthDate=" + birthDate +
                ", nacionality='" + nationality + '\'' +
                ", numberOfDependents=" + numberOfDependents +
                ", divorceLegalAgreement='" + divorceLegalAgreement + '\'' +
                ", dwellingType='" + dwellingType + '\'' +
                ", isDwellingFreeOfEncumbrance=" + isDwellingFreeOfEncumbrance +
                ", occupation='" + occupation + '\'' +
                ", startDateInCurrentJob=" + startDateInCurrentJob +
                ", currentPositionName='" + currentPositionName + '\'' +
                ", CurrentMonthlyIncome=" + currentMonthlyIncome +
                ", doesBizActivities=" + doesBizActivities +
                ", hiringType='" + hiringType + '\'' +
                ", externalEmployeeNumber='" + externalEmployeeNumber + '\'' +
                ", gender='" + gender + '\'' +

                /* ",currenci2='"+currenci2+'\''+*/
                '}';
    }
}
