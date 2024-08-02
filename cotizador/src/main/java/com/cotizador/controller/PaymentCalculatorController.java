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
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/creditos/auto")
public class PaymentCalculatorController {
    IndividualService individualService;
    PaymentCalculatorService paymentCalculatorService;
    ChargeService chargeService;

    PaymentDayService paymentDayService;

    BrandService brandService;
    ModelService modelService;

    TaxesService taxesService;


    @Value("${fixed.rate}")
    double rateFixed=0;
    @Value("${year.car}")
    int totalYears=0;
    List<Integer> yearsVehicle;



    public PaymentCalculatorController(IndividualService individualService, PaymentCalculatorService paymentCalculatorService, ChargeService chargeService,
                                       PaymentDayService paymentDayService, BrandService brandService,
                                       ModelService modelService,List<Integer> yearsVehicle, TaxesService taxesService

    ) {
        this.individualService = individualService;
        this.paymentCalculatorService = paymentCalculatorService;
        this.chargeService = chargeService;
        this.paymentDayService = paymentDayService;
        this.brandService = brandService;
        this.modelService = modelService;
        this.yearsVehicle = yearsVehicle;
        this.taxesService=taxesService;
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
        System.out.println("BrandId "+brandId);
        Brands brands = brandService.findById(brandId);

        if (brands==null || brands.getBrandId()==0){
            System.out.println("Marca de auto no encontrada");
        }
        List<Models> theModels=modelService.findByBrandId(brandId);

        System.out.println("id "+theModels.get(0).getModelId()+" name "+theModels.get(0).getName());
        return theModels;

    }
    @GetMapping("/simulador-credito-automotriz")
    public String addPaymentCalculator(Model theModel){

        Individual individual=new Individual();
        Brands brands =new Brands();

        //Get all brands from DB and fill to List
        List<Brands> listOfBrands=brandService.findAll();


        //Get all models from DB and fill to list
        // Inicialmente, cargar los productos de la primera categoría (si existe)
        if (!listOfBrands.isEmpty()) {
            Brands firstBrand = listOfBrands.get(0); // seleccionar la primera categoría por defecto
            List<Models> listOfModels = modelService.findByBrandId(firstBrand.getBrandId());
            theModel.addAttribute("theModels", listOfModels);
        }

        //get years of vehicle
        yearsVehicle=getYear(totalYears);

        //Add models to view
        theModel.addAttribute("theBrands",listOfBrands);
        theModel.addAttribute("individual", individual);
        theModel.addAttribute("theYearsVehicle",yearsVehicle);

        //theModel.addAttribute("theModels",models);

        return "paymentcalculator/Add-PaymentCalculator";
    }


    @PostMapping("/save")
    public String saveIndividual(@Valid @ModelAttribute("individual") Individual theIndividual,
                                 BindingResult theBindingResult, Model theModel){

        // get a date
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime dateNextMonth ;

        BigDecimal interestPeriod; //interes del periodo
        BigDecimal ivaInterestPeriod;

        BigDecimal  commisionForOpening; //comision por apertura
        BigDecimal ivaCommisionForOpening;

        if( theBindingResult.hasErrors()){
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
            Date date = new Date();

            List<PaymentCalculator> paymentCalculatorList = new ArrayList<>();

//*********************************** fill Entity paymentCalculator ***************************************************************
            PaymentCalculator paymentCalculator = new PaymentCalculator(
                    date,
                    theIndividual.getPaymentCalculadors().get(0).getYearVehicle(),
                    theIndividual.getPaymentCalculadors().get(0).getVehiclePrice(),
                    theIndividual.getPaymentCalculadors().get(0).getDownPayment(),
                    //theIndividual.getPaymentCalculadors().get(0).getPersonId(),
                    theIndividual.getPaymentCalculadors().get(0).getBrandId(),
                    theIndividual.getPaymentCalculadors().get(0).getModelId(),
                    theIndividual.getPaymentCalculadors().get(0).getLoanTerm(),
                    1,
                    rateFixed,
                    0);

//***********************************************************************************************************************************
            //Get calculation Value for "Comision por apertura"


            //To get the actual value of IVA
            Taxes taxes=taxesService.findByName("IVA");
            BigDecimal value = new BigDecimal(100);//porcentaje 100% pass to BigDecimal


//**************************************************  interestPeriod  ******************************************************************
            //Get Day of payment
            List<PaymentDay> paymentDay = paymentDayService.findByDayToExecute(true);

            //get days
            long daysToCalculateInterest = getDays(paymentDay.get(0).getPaymentDay());

            daysToCalculateInterest=20;
            //calculate interest of period for the initial charges
            interestPeriod = calculateInterest(paymentCalculator.calculateAmountCredit(), paymentCalculator.getRateValue(), daysToCalculateInterest);
            ivaInterestPeriod=interestPeriod.multiply(BigDecimal.valueOf(taxes.getValue())).divide(value, RoundingMode.HALF_UP);



//*********************************************************************************************************************************
            Charges chargeInterestPeriod = chargeService.findByName("Intereses del Periodo");

            //Ad charge commision For Openning
            ChargesReceivable chargesReceivable=new ChargesReceivable(
                    chargeInterestPeriod.getChargesId(),
                  date,
                  date,
                    interestPeriod,
                    interestPeriod,
                    ivaInterestPeriod,
                    ivaInterestPeriod,
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
            paymentCalculator.addChargesReceivable(chargesReceivable);

//******************************************  commision For Openning  ************************************************************
            Charges charges = chargeService.findByName("COMISION POR APERTURA");

            //calculate commision For Openning
            commisionForOpening=charges.getComisionXApertura(paymentCalculator.calculateAmountCredit());
            System.out.println("comision:"+commisionForOpening);

            //calculate IVA commision For Openning
            ivaCommisionForOpening=commisionForOpening.multiply(BigDecimal.valueOf(taxes.getValue())).divide(value, RoundingMode.HALF_UP);
            System.out.println("comision IVA :"+ivaCommisionForOpening);

//*********************************************************************************************************************************


            Charges chargeCommisionForOpening = chargeService.findByName("COMISION POR APERTURA");

            //Ad charge commision For Openning
            ChargesReceivable chargesReceivable2=new ChargesReceivable(
                    chargeCommisionForOpening.getChargesId(),
                    date,
                    date,
                    commisionForOpening,
                    commisionForOpening,
                    ivaCommisionForOpening,
                    ivaCommisionForOpening,
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
            paymentCalculator.addChargesReceivable(chargesReceivable);
            paymentCalculator.addChargesReceivable(chargesReceivable2);

            
//*************************************  SAVE INDIVIDUAL AND PAYMENT CALCULATOR   *******************************************************
            //fill individual paymentCalculator and add paymentCalculator;
            paymentCalculatorList.add(paymentCalculator);
            // save the individual
           theIndividual.setPaymentCalculadors(paymentCalculatorList);
           individualService.save(theIndividual);
//***********************************************************************************************************************************

            dateNextMonth=getPaymentNextMonth(paymentDay.get(0).getPaymentDay());

            System.out.println("Fecha de hoy: "+today);
            System.out.println("Fecha siguiente mes: "+dateNextMonth);




            //add models to view
            theModel.addAttribute("thePaymentCalculator", paymentCalculator);
            theModel.addAttribute("theCharges", charges);
            theModel.addAttribute("theInterestPeriod", interestPeriod);
            theModel.addAttribute("theIvaInterestPeriod", ivaInterestPeriod);
            theModel.addAttribute("theCommisionForOpening", commisionForOpening);
            theModel.addAttribute("theIvaCommisionForOpening", ivaCommisionForOpening);

            //return "paymentcalculator/Show-PaymentCalculator";
           return "test/test2";
        }
    }

    //get number of days to calculate interest of period
    public long getDays(int day){

        //validate day
        if (day <1){
            return 0;
        }

        // get a reference to today
        LocalDateTime today = LocalDateTime.now();

        // having today,
        LocalDateTime firstOfNextMonth = today
                // add one to the month
                .withMonth(today.getMonthValue() + 1)
                // and take the first day of that month
                .withDayOfMonth(day);

        //get differences between today and paymentday from nex month
        long daysBetween=0;
        try {
            daysBetween = Duration.between(today, firstOfNextMonth).toDays();
            return daysBetween;
        }
        catch (DateTimeParseException e){
            System.out.println(e.getParsedString());
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

    public BigDecimal calculateInterest(BigDecimal amountCredit, double rateFixed, long daysOfInteres){

        //para calcular en porcentajes
        rateFixed=rateFixed/100;
        BigDecimal rate = new BigDecimal(rateFixed);

        BigDecimal daysOfYears = new BigDecimal(360);
        BigDecimal daysOfInt = new BigDecimal(daysOfInteres);
        BigDecimal interestPeriod=amountCredit.multiply(rate).divide(daysOfYears, RoundingMode.HALF_UP ).multiply(daysOfInt);

        interestPeriod = interestPeriod.setScale(2, RoundingMode.CEILING);
        return interestPeriod;
    }

    //get years of vehicle
    public List<Integer> getYear(int totalYears){
        LocalDateTime beforeOfNextMonth = LocalDateTime.now();
        List<Integer> years=new ArrayList<>();
        for (int i=totalYears-1; i>=0;i--){
            years.add(beforeOfNextMonth.minusYears(i).getYear());
            beforeOfNextMonth.minusYears(i);
            //System.out.println( beforeOfNextMonth.minusYears(i).getYear());
        }
        return years;
    }

}

