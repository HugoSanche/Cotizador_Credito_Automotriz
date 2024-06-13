package com.cotizador.controller;

import com.cotizador.entity.Individual;
import com.cotizador.entity.PaymentCalculator;
import com.cotizador.service.IndividualService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
        PaymentCalculator paymentCalculator=new PaymentCalculator();
        return "paymentcalculator/Add-PaymentCalculator";
    }
    @PostMapping("/save")
    public String saveIndividual(@ModelAttribute("individual") Individual theIndividual){

        // System.out.println("getCurrentMonthlyIncome "+theIndividual.getCurrentMonthlyIncome());
        //save the individual
        individualService.save(theIndividual);

        // use a redirect to prevent duplicate submissions
        return "redirect:/individuals/list";
    }

}
