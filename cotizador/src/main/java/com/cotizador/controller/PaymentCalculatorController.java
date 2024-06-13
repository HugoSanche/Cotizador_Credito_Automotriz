package com.cotizador.controller;

import com.cotizador.entity.Individual;
import com.cotizador.entity.PaymentCalculator;
import com.cotizador.service.IndividualService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/creditos/auto")
public class PaymentCalculatorController {
    IndividualService individualService;

    public PaymentCalculatorController(IndividualService individualService) {
        this.individualService = individualService;
    }

    public IndividualService getIndividualService() {
        return individualService;
    }

    public void setIndividualService(IndividualService individualService) {
        this.individualService = individualService;
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
