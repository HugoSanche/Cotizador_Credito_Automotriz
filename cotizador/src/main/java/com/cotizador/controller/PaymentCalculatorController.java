package com.cotizador.controller;

import com.cotizador.entity.Individual;
import com.cotizador.entity.PaymentCalculator;
import com.cotizador.service.IndividualService;
import jakarta.validation.Valid;
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

    public PaymentCalculatorController(IndividualService individualService) {
        this.individualService = individualService;
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
    @PostMapping("/save")
    public String saveIndividual(@Valid @ModelAttribute("individual") Individual theIndividual, BindingResult theBindingResult){

        if( theBindingResult.hasErrors()){
            return "paymentcalculator/Add-PaymentCalculator";
        }
        else{
            //fill null values
            theIndividual.setDwellingType("NotApplies");
            theIndividual.setMaritalStatus("Unknoww");
            theIndividual.setNumberOfDependents(0);
            theIndividual.setHiringType("Normalpayroll");
            System.out.println(theIndividual.getPaymentCalculadors().get(0).getYearVehicle());

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            System.out.println(formatter.format(date));

            List<PaymentCalculator> paymentCalculatorList=new ArrayList<>();

            PaymentCalculator paymentCalculator=new PaymentCalculator(
                    date,
                    theIndividual.getPaymentCalculadors().get(0).getYearVehicle(),
                    theIndividual.getPaymentCalculadors().get(0).getVehiclePrice(),
                    theIndividual.getPaymentCalculadors().get(0).getDownPayment(),
                    //theIndividual.getPersonId(),
                    0,
                    0,
                    theIndividual.getPaymentCalculadors().get(0).getLoanTerm(),
                    0);
            paymentCalculatorList.add(paymentCalculator);
            System.out.println("getCurrentMonthlyIncome "+theIndividual.getCurrentMonthlyIncome());
            // save the individual
            theIndividual.setPaymentCalculadors(paymentCalculatorList);
            individualService.save(theIndividual);
            return "redirect:/individuals/list";
        }

    }

}
