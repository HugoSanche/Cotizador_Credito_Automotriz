package com.cotizador.controller;

import com.cotizador.entity.*;
import com.cotizador.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/creditos/auto")
public class PaymentCalculatorController {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    IndividualService individualService;
    PaymentCalculatorService paymentCalculatorService;
    ChargeService chargeService;
    PaymentDayService paymentDayService;
    BrandService brandService;
    ModelService modelService;
    TaxesService taxesService;
    ScheduledPaymentService scheduledPaymentService;

    @Value("${fixed.rate}")
    double rateFixed=0;
    @Value("${year.car}")
    int totalYears=0;
    List<Integer> yearsVehicle;

    @Value("${frequency}")
    double frequency=0;

    int personId;
    BigDecimal value = new BigDecimal(100);//porcentaje 100% pass to BigDecimal

    public PaymentCalculatorController(IndividualService individualService, PaymentCalculatorService paymentCalculatorService, ChargeService chargeService,
                                       PaymentDayService paymentDayService, BrandService brandService,
                                       ModelService modelService,List<Integer> yearsVehicle, TaxesService taxesService, ScheduledPaymentService scheduledPaymentService

    ) {
        this.individualService = individualService;
        this.paymentCalculatorService = paymentCalculatorService;
        this.chargeService = chargeService;
        this.paymentDayService = paymentDayService;
        this.brandService = brandService;
        this.modelService = modelService;
        this.yearsVehicle = yearsVehicle;
        this.taxesService=taxesService;
        this.scheduledPaymentService=scheduledPaymentService;
    }


    // add an initbinder ... to convert trim input string
    //remove leading and training white spaces
    //resolve issue for our validation
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmesrEditor=new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmesrEditor);

    }
    @GetMapping("/modelos/{brandId}")
    @ResponseBody
    public List<Models> obtenerProductosPorCategoria(@PathVariable int brandId) {
        Brands brands = brandService.findById(brandId);

        if (brands==null || brands.getBrandId()==0){
            System.out.println("Marca de auto no encontrada");
        }
        List<Models> theModels=modelService.findByBrandId(brandId);

        return theModels;

    }
    @GetMapping("/simulador-credito-automotriz")
    public String addPaymentCalculator(Model theModel){

        Individual individual=new Individual();

        //Get all brands from DB and fill to List
        List<Brands> listOfBrands=brandService.findAll();

        //Get all models from DB and fill to list
        // Inicialmente, cargar los productos de la primera categoría (si existe)
        if (!listOfBrands.isEmpty()) {
            Brands firstBrand = listOfBrands.get(0); // seleccionar la primera categoría por defecto
            List<Models> listOfModels = modelService.findByBrandId(firstBrand.getBrandId());
            theModel.addAttribute("theListOfModels", listOfModels);
        }

        //get years of vehicle
        yearsVehicle=getYear(totalYears);


        //Add models to view
        theModel.addAttribute("theListOfBrands",listOfBrands);
        theModel.addAttribute("theIndividual", individual);
        theModel.addAttribute("theYearsVehicle",yearsVehicle);

        //theModel.addAttribute("theModels",models);

        return "paymentcalculator/Add-Individual";
       // return "paymentcalculator/Add-PaymentCalculator";
    }

    @PostMapping("/saveIndividual")
    public String saveIndividual(@Valid @ModelAttribute("theIndividual") Individual theIndividual,
                                 BindingResult theBindingResultIndividual,
                                 Model theModel){

        if( theBindingResultIndividual.hasErrors()){
            System.out.println("Error Individual");

            return "paymentcalculator/Add-Individual";
        }
        else {
            //fill null values
            theIndividual.setDwellingType("NotApplies");
            theIndividual.setMaritalStatus("Unknoww");
            theIndividual.setNumberOfDependents(0);
            theIndividual.setHiringType("Normalpayroll");

            individualService.save(theIndividual);

            PaymentCalculator paymentCalculator=new PaymentCalculator();

            //Get all brands from DB and fill to List
            List<Brands> listOfBrands=brandService.findAll();

            //Get all models from DB and fill to list
            // Inicialmente, cargar los productos de la primera categoría (si existe)
            if (!listOfBrands.isEmpty()) {
                Brands firstBrand = listOfBrands.get(0); // seleccionar la primera categoría por defecto
                List<Models> listOfModels = modelService.findByBrandId(firstBrand.getBrandId());
                theModel.addAttribute("theListOfModels", listOfModels);
            }

            //get years of vehicle
            yearsVehicle=getYear(totalYears);

            //add models to view
            theModel.addAttribute("theListOfBrands",listOfBrands);
            theModel.addAttribute("thePaymentCalculator",paymentCalculator);
            theModel.addAttribute("theChargeService", chargeService);
            theModel.addAttribute("theIndividual", theIndividual);
            theModel.addAttribute("theBrands",brandService);
            theModel.addAttribute("theModels",modelService);
            theModel.addAttribute("theYearsVehicle",yearsVehicle);

            personId= theIndividual.getPersonId();

           // return "paymentcalculator/Show-PaymentCalculator";
            //return "test/Show";
            return "paymentcalculator/Add-PaymentCalculator2";
        }
    }

    @PostMapping("/savePaymentCalculator")
    public String savePaymentCalculator(
                                  @Valid @ModelAttribute("thepaymentcalculator") PaymentCalculator thepaymentcalculator,
                                  BindingResult theBindingResultPaymentCalculator,
                                  Model theModel){

        Individual theIndividual=individualService.findById(personId);
        // get a date
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime dateNextMonth ;

        BigDecimal interestPeriod; //interes del periodo
        BigDecimal ivaInterestPeriod;

        BigDecimal  commisionForOpening; //comision por apertura
        BigDecimal ivaCommisionForOpening;

        if( theBindingResultPaymentCalculator.hasErrors()){
            System.out.println("Error savePaymentCalculator");

            //Get all brands from DB and fill to List
            List<Brands> listOfBrands=brandService.findAll();

            //Get all models from DB and fill to list
            // Inicialmente, cargar los productos de la primera categoría (si existe)
            if (!listOfBrands.isEmpty()) {
                Brands firstBrand = listOfBrands.get(0); // seleccionar la primera categoría por defecto
                List<Models> listOfModels = modelService.findByBrandId(thepaymentcalculator.getBrandId());
                theModel.addAttribute("theListOfModels", listOfModels);
            }
            else{
                List<Models> listOfModels =modelService.findByModelId(thepaymentcalculator.getBrandId());
                theModel.addAttribute("theListOfModels", listOfModels);
            }
            yearsVehicle=getYear(totalYears);
          //  System.out.println("listOfBrands "+listOfBrands.get(0));
            theModel.addAttribute("thePaymentCalculator",thepaymentcalculator);

            theModel.addAttribute("theListOfBrands",listOfBrands);
            //theModel.addAttribute("theModels", listOfModels);
            theModel.addAttribute("theYearsVehicle",yearsVehicle);
            theModel.addAttribute("theIndividual", theIndividual);

            return "paymentcalculator/Add-PaymentCalculator2";
        }
        else {
            //fill null values

            LocalDateTime date = LocalDateTime.now();

            List<PaymentCalculator> paymentCalculatorList = new ArrayList<>();

//*********************************** fill Entity paymentCalculator ***************************************************************
            PaymentCalculator paymentCalculator = new PaymentCalculator(
                    date,
                    thepaymentcalculator.getYearVehicle(),
                    thepaymentcalculator.getVehiclePrice(),
                    thepaymentcalculator.getDownPayment(),
                    personId,
                    thepaymentcalculator.getBrandId(),
                    thepaymentcalculator.getModelId(),
                    thepaymentcalculator.getLoanTerm(),
                    1,
                    rateFixed,
                    0
                   );

//***********************************************************************************************************************************
            //To get the actual value of IVA
            Taxes taxes=taxesService.findByName("IVA");

//**************************************************  interestPeriod  ******************************************************************
            //Get Day of payment
            List<PaymentDay> paymentDay = paymentDayService.findByDayToExecute(true);
            int dayToPayment=paymentDay.get(0).getPaymentDay();

            //get days of interest
            int daysToCalculateInterest = getDays(dayToPayment);

            daysToCalculateInterest=20;


            //calculate interest of period for the initial charges
            interestPeriod = calculateInterest(paymentCalculator.calculateAmountCredit(), paymentCalculator.getRateValue(), daysToCalculateInterest);
            ivaInterestPeriod=calculateIvaInterest(interestPeriod, taxes);

//*********************************************************************************************************************************
            Charges chargeInterestPeriod = chargeService.findByName("Intereses del Periodo");

            //Ad charge commision For Openning
            ChargesReceivable interesesDelPeriodo;

            //add to DB intereses del periodo
            interesesDelPeriodo=createChargesReceivable(chargeInterestPeriod.getChargesId(),date, interestPeriod,ivaInterestPeriod);

            // interesesDelPeriodo.addCharges(chargeInterestPeriod);
            paymentCalculator.addChargesReceivable(interesesDelPeriodo);


//******************************************  Comision por apertura  ************************************************************
            //Get calculation Value for "Comision por apertura"
            Charges chargeCommisionForOpening = chargeService.findByName("COMISION POR APERTURA");

            //calculate commision For Openning
            commisionForOpening=calculateCommisionForOpening(paymentCalculator.calculateAmountCredit(),chargeCommisionForOpening);

            //calculate IVA commision For Openning
            ivaCommisionForOpening=calculateIvaCommisionForOpening(commisionForOpening, taxes);

//*********************************************************************************************************************************

            //Ad charge commision For Openning
            ChargesReceivable comisionXApertura;

            //add to DB comission por apertura
            comisionXApertura=createChargesReceivable(chargeCommisionForOpening.getChargesId(),date, commisionForOpening,ivaCommisionForOpening);

            paymentCalculator.addChargesReceivable(comisionXApertura);

//*************************************  SAVE INDIVIDUAL AND PAYMENT CALCULATOR   *******************************************************
            //fill individual paymentCalculator and add paymentCalculator;
            paymentCalculatorList.add(paymentCalculator);

            //add paymentcalculator to individual

            // save the individual
            paymentCalculatorService.save(paymentCalculator);
//***********************************************************************************************************************************
            dateNextMonth=getPaymentNextMonth(paymentDay.get(0).getPaymentDay());

            createScheduledPayment(paymentCalculator.calculateAmountCredit(),paymentCalculator.getPaymentCalculatorId(),
                    paymentCalculator.getRateValue(), daysToCalculateInterest,paymentCalculator.getLoanTerm(),
                    date, dayToPayment);

            //add models to view
            List<ScheduledPayment> scheduledPayment= scheduledPaymentService.find(paymentCalculator.getPaymentCalculatorId());

            theModel.addAttribute("theScheduledPayment", scheduledPayment);
            theModel.addAttribute("theCharges2", paymentCalculator.getChargesReceivable());
            theModel.addAttribute("theChargeService", chargeService);
           // theModel.addAttribute("theIndividual", theIndividual);
            theModel.addAttribute("theBrands",brandService);
            theModel.addAttribute("theModels",modelService);
            theModel.addAttribute("thePaymentCalculator", paymentCalculator);
            theModel.addAttribute("theIndividual", theIndividual);
            theModel.addAttribute("theCharges", chargeCommisionForOpening);
            theModel.addAttribute("theInterestPeriod", interestPeriod);
            theModel.addAttribute("theIvaInterestPeriod", ivaInterestPeriod);
            theModel.addAttribute("theCommisionForOpening", commisionForOpening);
            theModel.addAttribute("theIvaCommisionForOpening", ivaCommisionForOpening);

            //return "paymentcalculator/Show-PaymentCalculator";
            return "test/show";
        }
    }

    @PostMapping("/save")
    public String saveIndividual2(@Valid @ModelAttribute("theIndividual") Individual theIndividual,
                                 BindingResult theBindingResultIndividual,
                                 Model theModel){
        // get a date
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime dateNextMonth ;

        BigDecimal interestPeriod; //interes del periodo
        BigDecimal ivaInterestPeriod;

        BigDecimal  commisionForOpening; //comision por apertura
        BigDecimal ivaCommisionForOpening;

        if( theBindingResultIndividual.hasErrors()){
            yearsVehicle=getYear(totalYears);
            System.out.println("Error ");

            List<Brands> listOfBrands=brandService.findAll();

            List<Models> listOfModels =modelService.findByModelId(theIndividual.getPaymentCalculadors().get(0).getModelId());
            theModel.addAttribute("theBrands",listOfBrands);
            theModel.addAttribute("theModels", listOfModels);
            theModel.addAttribute("theYearsVehicle",yearsVehicle);

            return "paymentcalculator/Add-PaymentCalculator";
        }
        else {
            //fill null values
            theIndividual.setDwellingType("NotApplies");
            theIndividual.setMaritalStatus("Unknoww");
            theIndividual.setNumberOfDependents(0);
            theIndividual.setHiringType("Normalpayroll");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            LocalDateTime date =LocalDateTime.now();

            List<PaymentCalculator> paymentCalculatorList = new ArrayList<>();

//*********************************** fill Entity paymentCalculator ***************************************************************
            PaymentCalculator paymentCalculator = new PaymentCalculator(
                    date,
                    theIndividual.getPaymentCalculadors().get(0).getYearVehicle(),
                    theIndividual.getPaymentCalculadors().get(0).getVehiclePrice(),
                    theIndividual.getPaymentCalculadors().get(0).getDownPayment(),
                    personId,
                    theIndividual.getPaymentCalculadors().get(0).getBrandId(),
                    theIndividual.getPaymentCalculadors().get(0).getModelId(),
                    theIndividual.getPaymentCalculadors().get(0).getLoanTerm(),
                    1,
                    rateFixed,
                    0);

//***********************************************************************************************************************************

            //To get the actual value of IVA
            Taxes taxes=taxesService.findByName("IVA");


//**************************************************  interestPeriod  ******************************************************************
            //Get Day of payment
            List<PaymentDay> paymentDay = paymentDayService.findByDayToExecute(true);

            int dayToPayment=paymentDay.get(0).getPaymentDay();
            //get days
            int daysToCalculateInterest = getDays(dayToPayment);

            System.out.println(daysToCalculateInterest);
           // daysToCalculateInterest=20;

            //calculate interest of period for the initial charges
            interestPeriod = calculateInterest(paymentCalculator.calculateAmountCredit(), paymentCalculator.getRateValue(), daysToCalculateInterest);
            ivaInterestPeriod=calculateIvaInterest(interestPeriod, taxes);


//*********************************************************************************************************************************
            Charges chargeInterestPeriod = chargeService.findByName("Intereses del Periodo");

            //Ad charge commision For Openning
            ChargesReceivable interesesDelPeriodo;

            //add to DB intereses del periodo
            interesesDelPeriodo=createChargesReceivable(chargeInterestPeriod.getChargesId(),date, interestPeriod,ivaInterestPeriod);

            System.out.println("charge "+chargeInterestPeriod.getName());
           // interesesDelPeriodo.addCharges(chargeInterestPeriod);
            paymentCalculator.addChargesReceivable(interesesDelPeriodo);


//******************************************  Comision por apertura  ************************************************************
            //Get calculation Value for "Comision por apertura"
            Charges chargeCommisionForOpening = chargeService.findByName("COMISION POR APERTURA");

            //calculate commision For Openning
            commisionForOpening=calculateCommisionForOpening(paymentCalculator.calculateAmountCredit(),chargeCommisionForOpening);

            //calculate IVA commision For Openning
            ivaCommisionForOpening=calculateIvaCommisionForOpening(commisionForOpening, taxes);


//*********************************************************************************************************************************
            //Add charge commision For Openning
            ChargesReceivable comisionXApertura;

            //add to DB comission por apertura
            comisionXApertura=createChargesReceivable(chargeCommisionForOpening.getChargesId(),date, commisionForOpening,ivaCommisionForOpening);

           paymentCalculator.addChargesReceivable(comisionXApertura);

//*************************************  SAVE INDIVIDUAL AND PAYMENT CALCULATOR   *******************************************************
            //fill individual paymentCalculator and add paymentCalculator;
            paymentCalculatorList.add(paymentCalculator);

            //add paymentcalculator to individual
           theIndividual.setPaymentCalculadors(paymentCalculatorList);

            // save the individual
           individualService.save(theIndividual);
//***********************************************************************************************************************************

            //Create table amortizacion
            createScheduledPayment(paymentCalculator.calculateAmountCredit(), paymentCalculator.getPaymentCalculatorId(),
                    paymentCalculator.getRateValue(), daysToCalculateInterest,paymentCalculator.getLoanTerm(),
                    date, dayToPayment);

            //add models to view
            theModel.addAttribute("theCharges2", paymentCalculator.getChargesReceivable());
            theModel.addAttribute("theChargeService", chargeService);
            theModel.addAttribute("theIndividual", theIndividual);
            theModel.addAttribute("theBrands",brandService);
            theModel.addAttribute("theModels",modelService);
            theModel.addAttribute("thePaymentCalculator", paymentCalculator);
            theModel.addAttribute("theCharges", chargeCommisionForOpening);
            theModel.addAttribute("theInterestPeriod", interestPeriod);
            theModel.addAttribute("theIvaInterestPeriod", ivaInterestPeriod);
            theModel.addAttribute("theCommisionForOpening", commisionForOpening);
            theModel.addAttribute("theIvaCommisionForOpening", ivaCommisionForOpening);

            //return "paymentcalculator/Show-PaymentCalculator";
           return "test/Show";
        }
    }

    //get number of days to calculate interest of period
    public int getDays(int day){

        //validate day
        if (day <1){
            return 0;
        }
        // get a reference to today
        LocalDateTime today = LocalDateTime.now();
        int daysBetween = 0;
        if (today.getDayOfMonth()>day) {
            // having today,
            LocalDateTime firstOfNextMonth = today
                    // add one to the month
                    .withMonth(today.getMonthValue() + 1)

                    // and take the first day of that month
                    .withDayOfMonth(day);

            //get differences between today and paymentday from nex month
            try {
                daysBetween = (int) Duration.between(today, firstOfNextMonth).toDays();
                return daysBetween;
            } catch (DateTimeParseException e) {
                System.out.println(e.getParsedString());
            }
        }
        else{
            //get differences between today and paymentday from nex month
            try {System.out.println("getDayOfMonth() "+today.getDayOfMonth());
                daysBetween = day-today.getDayOfMonth();
                return daysBetween;
            } catch (DateTimeParseException e) {
                System.out.println(e.getParsedString());
            }
        }
        return daysBetween;
    }


    //get date of payment of next month
    public LocalDateTime getPaymentNextMonth(int day){

        //validate day
        if (day <1){
            //regresa la fecha de hoy
            return LocalDateTime.now();
        }

        // get a reference to today
        LocalDateTime today = LocalDateTime.now();

        // having today,
        LocalDateTime dateOfNextMonth = today
                // add one to the month
                .withMonth(today.getMonthValue() + 1)
                // and take the first day of that month
                .withDayOfMonth(day);

        return dateOfNextMonth;
    }

    //get years of vehicle
    public List<Integer> getYear(int totalYears){
        LocalDateTime beforeOfNextMonth = LocalDateTime.now();
        List<Integer> years=new ArrayList<>();
        for (int i=totalYears-1; i>=0;i--){
            years.add(beforeOfNextMonth.minusYears(i).getYear());
            beforeOfNextMonth.minusYears(i);
        }
        return years;
    }
    public BigDecimal calculateCommisionForOpening(BigDecimal amountOfCredit, Charges  chargeCommisionForOpening){
        BigDecimal value = new BigDecimal(100);

        return amountOfCredit.multiply(chargeCommisionForOpening.getCalculationValue()).divide(value, RoundingMode.HALF_EVEN);
    }

    public  BigDecimal calculateIvaCommisionForOpening(BigDecimal commisionForOpening, Taxes taxes){
        BigDecimal ivaCommisionForOpening;
        ivaCommisionForOpening=commisionForOpening.multiply(BigDecimal.valueOf(taxes.getValue())).divide(value, RoundingMode.HALF_EVEN);
        return ivaCommisionForOpening;
    }
    public BigDecimal calculateInterest(BigDecimal amountCredit, double rateFixed, long daysOfInteres){

        //para calcular en porcentajes
        rateFixed=rateFixed/100;
        BigDecimal rate = new BigDecimal(rateFixed);

        BigDecimal daysOfYears = new BigDecimal(360);
        BigDecimal daysOfInt = new BigDecimal(daysOfInteres);
        BigDecimal interestPeriod=amountCredit.multiply(rate).divide(daysOfYears, RoundingMode.HALF_EVEN ).multiply(daysOfInt);

        interestPeriod = interestPeriod.setScale(2, RoundingMode.HALF_EVEN);
        return interestPeriod;
    }
    public BigDecimal calculateIvaInterest(BigDecimal interestPeriod, Taxes taxes){
        BigDecimal ivainterestPeriod;
        //Get IVA and divide 100
        ivainterestPeriod=interestPeriod.multiply(BigDecimal.valueOf(taxes.getValue())).divide(value, RoundingMode.HALF_EVEN);

        return ivainterestPeriod;
    }



    public ChargesReceivable createChargesReceivable(int ChargesId, LocalDateTime date, BigDecimal chargeAmount, BigDecimal ivaChargeAmount){
        ChargesReceivable chargesReceivable=new ChargesReceivable(
                ChargesId,
                date,
                date,
                chargeAmount,
                chargeAmount,
                ivaChargeAmount,
                ivaChargeAmount,
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(0),
                "",
                date,
                date,
                "Active"
        );
        return chargesReceivable;
    }
/*
* Crea la tabla de amortizacion
* */
    public void createScheduledPayment( BigDecimal amountCredit,  int paymentCalculatorId, Double rateFixed,
                                        int daysOfInteres, int plazo, LocalDateTime date, int dayToPayment)
    {
        rateFixed=rateFixed/100;
        BigDecimal ii=BigDecimal.valueOf(rateFixed/frequency);
        BigDecimal valor1=(ii.add(BigDecimal.valueOf(1)));
        BigDecimal x1=valor1.pow(plazo);
        valor1=ii.multiply(x1);


        BigDecimal valor2=ii.add(BigDecimal.valueOf(1));
        BigDecimal x2=valor2.pow((plazo));
        valor2=x2.subtract(BigDecimal.valueOf(1));


        BigDecimal valor3=valor1.divide(valor2, RoundingMode.HALF_EVEN);
        BigDecimal capitalAmount=BigDecimal.valueOf(0);

        BigDecimal contractInitialBalance=amountCredit;
        BigDecimal contractFinalBalance=amountCredit;
        BigDecimal interest=BigDecimal.valueOf(0);
        BigDecimal rate = new BigDecimal(rateFixed);
        BigDecimal cuotaNivelada=BigDecimal.valueOf(0);

        for (int i=0;i<plazo+1;i++){
            if (i<=0){
                contractInitialBalance=amountCredit;
                contractFinalBalance= amountCredit;
            } else if (i==1) {

                System.out.println("Renta "+i);
                interest = (contractFinalBalance.multiply(rate)).divide(BigDecimal.valueOf(plazo), 2, RoundingMode.HALF_EVEN);
                System.out.println("interest "+interest);

                capitalAmount=amountCredit.multiply(valor3);
                System.out.println("capitalAmount "+capitalAmount);

                cuotaNivelada=capitalAmount;

                capitalAmount=capitalAmount.subtract(interest);
                System.out.println("capitalAmount "+capitalAmount);

                System.out.println("contractFinalBalance "+contractFinalBalance);
                contractFinalBalance=contractFinalBalance.subtract(capitalAmount);
                System.out.println("contractFinalBalance "+contractFinalBalance);

                System.out.println("------------------------------");
            } else{
                contractFinalBalance=contractFinalBalance.subtract(capitalAmount);
               // contractFinalBalance=contractFinalBalance.subtract(interest);
            }
            ScheduledPayment scheduledPayment=new ScheduledPayment(paymentCalculatorId,
                    i,
                    capitalAmount,
                    0,
                    capitalAmount,
                    interest,
                    BigDecimal.valueOf(0),
                    interest,
                    BigDecimal.valueOf(0),
                    BigDecimal.valueOf(0),
                    BigDecimal.valueOf(0),
                    BigDecimal.valueOf(0),
                    BigDecimal.valueOf(0),
                    BigDecimal.valueOf(0),
                    capitalAmount,
                    date,
                    "Activo",
                    contractInitialBalance,
                    contractFinalBalance,
                    BigDecimal.valueOf(0),
                    BigDecimal.valueOf(0),
                    null
            );
            date=date.plusMonths(1).withDayOfMonth(dayToPayment);
            scheduledPaymentService.save(scheduledPayment);

                interest = (contractFinalBalance.multiply(rate)).divide(BigDecimal.valueOf(plazo), 2, RoundingMode.HALF_EVEN);
                capitalAmount =cuotaNivelada.subtract(interest);
                contractInitialBalance = contractFinalBalance;
                System.out.println("Renta "+i);
                System.out.println("interest "+interest);
                System.out.println("capitalAmount "+capitalAmount);


        }

    }
    public String getCharges(int chargesId ){
        return chargeService.getName(chargesId);
    }
}

