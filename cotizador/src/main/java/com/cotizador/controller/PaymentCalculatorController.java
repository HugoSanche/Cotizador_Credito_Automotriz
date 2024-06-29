package com.cotizador.controller;

import com.cotizador.entity.Charges;
import com.cotizador.entity.Individual;
import com.cotizador.entity.PaymentCalculator;
import com.cotizador.entity.PaymentDay;
import com.cotizador.service.ChargeService;
import com.cotizador.service.IndividualService;
import com.cotizador.service.PaymentCalculatorService;
import com.cotizador.service.PaymentDayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

    @Value("${fixed.rate}")
    int rateFixed=0;
    @Value("${year.car}")
    int totalYears=0;

    public PaymentCalculatorController(IndividualService individualService, PaymentCalculatorService paymentCalculatorService,
                                       ChargeService chargeService, PaymentDayService paymentDayService) {
        this.individualService = individualService;
        this.paymentCalculatorService = paymentCalculatorService;
        this.chargeService = chargeService;
        this.paymentDayService = paymentDayService;
    }

    // add an initbinder ... to convert trim input string
    //remove leading and training white spaces
    //resolve issue for our validation
    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmesrEditor=new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmesrEditor);
    }


    @GetMapping("/simulador-credito-automotriz")
    public String addPaymentCalculator(Model theModel){
        List<Integer> yearsVehicle;
        Individual individual=new Individual();

        //get years of vehicle
        yearsVehicle=getYear(totalYears);


        theModel.addAttribute("theYearsVehicle",yearsVehicle);
        theModel.addAttribute("individual", individual);

        return "paymentcalculator/Add-PaymentCalculator";
    }


    @GetMapping("/showFormForPaymentCalculator")
    public String showFormForPaymentCalculator(@RequestParam("individualId") int theId, Model theModel){

        System.out.println("The Id "+theId);

        Individual individual=individualService.findById(theId);
        PaymentCalculator paymentCalculator=paymentCalculatorService.findById(theId);



        theModel.addAttribute("individual", individual);
        theModel.addAttribute("paymentCalculator",paymentCalculator);



        System.out.println("paymentCalculator.getYearVehicle()) "+paymentCalculator.getYearVehicle());
        return "paymentcalculator/Show-PaymentCalculator";
    }



    @PostMapping("/save")
    public String saveIndividual(@Valid @ModelAttribute("individual") Individual theIndividual,
                                 BindingResult theBindingResult,Model theModel){

        double interestPeriod=0;


        if( theBindingResult.hasErrors()){
            return "paymentcalculator/Add-PaymentCalculator";
        }
        else{
            //fill null values
            theIndividual.setDwellingType("NotApplies");
            theIndividual.setMaritalStatus("Unknoww");
            theIndividual.setNumberOfDependents(0);
            theIndividual.setHiringType("Normalpayroll");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            List<PaymentCalculator> paymentCalculatorList=new ArrayList<>();

            //fill entity paymentCalculator
            PaymentCalculator paymentCalculator=new PaymentCalculator(
                    date,
                    theIndividual.getPaymentCalculadors().get(0).getYearVehicle(),
                    theIndividual.getPaymentCalculadors().get(0).getVehiclePrice(),
                    theIndividual.getPaymentCalculadors().get(0).getDownPayment(),
                    0,
                    0,
                    theIndividual.getPaymentCalculadors().get(0).getLoanTerm(),
                    1,
                    rateFixed,
                    0);
            //fill individual paymentCalculator and add paymentCalculator;
            paymentCalculatorList.add(paymentCalculator);
            // save the individual
            theIndividual.setPaymentCalculadors(paymentCalculatorList);
            individualService.save(theIndividual);

            //Get calculation Value for "Comision por apertura"
            List<Charges> charges=chargeService.findByName("COMISION POR APERTURA");
            System.out.println("Charges "+charges.get(0).getCalculationValue());



            //Get Day of payment
           List<PaymentDay> paymentDay=paymentDayService.findByDayToExecute(true);
           // System.out.println(paymentDay.get(0).getPaymentDay());

            //get days
            long daysToCalculateInterest=getDays( paymentDay.get(0).getPaymentDay());

            //calculate interest of period from the initial charges
           interestPeriod= calculateInterest(paymentCalculator.calculateAmountCredit(), paymentCalculator.getRateValue(),daysToCalculateInterest);

            //add models to view

            theModel.addAttribute("thePaymentCalculator",paymentCalculator);
            theModel.addAttribute("theCharges",charges);
            theModel.addAttribute("theinterestPeriod",interestPeriod);

            return "paymentcalculator/Show-PaymentCalculator";
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

        //System.out.println( (firstOfNextMonth.format(DateTimeFormatter.ISO_LOCAL_DATE)));

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
    public double calculateInterest(double amountCredit, double rateFixed, long daysOfInteres){
        double interestPeriod=amountCredit*rateFixed/360*daysOfInteres;
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













