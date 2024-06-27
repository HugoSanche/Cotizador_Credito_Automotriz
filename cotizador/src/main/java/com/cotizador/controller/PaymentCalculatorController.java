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

    @Value("${dayOfPayment}")
      int DAYPAYMENT;

    public PaymentCalculatorController(IndividualService individualService, PaymentCalculatorService paymentCalculatorService,
                                       ChargeService chargeService, PaymentDayService paymentDayService,
                                       int dayOfPayment) {
        this.individualService = individualService;
        this.paymentCalculatorService = paymentCalculatorService;
        this.chargeService = chargeService;
        this.paymentDayService = paymentDayService;
        this.DAYPAYMENT = dayOfPayment;
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
        Individual individual=new Individual();

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
                    15,
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

            System.out.println("DayPayment "+DAYPAYMENT);
           List<PaymentDay> paymentDay=paymentDayService.findByDay(DAYPAYMENT);
            System.out.println(paymentDay.get(0).getPaymentDay());
            //add models to view
            theModel.addAttribute("thePaymentCalculator",paymentCalculator);
            theModel.addAttribute("theCharges",charges);

            return "paymentcalculator/Show-PaymentCalculator";
        }

    }

}
