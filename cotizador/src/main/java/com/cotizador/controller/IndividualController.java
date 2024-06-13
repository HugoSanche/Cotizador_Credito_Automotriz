package com.cotizador.controller;


import com.cotizador.entity.Individual;
import com.cotizador.service.IndividualService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/individuals")
public class IndividualController {
    IndividualService individualService;


    public IndividualController(IndividualService individualService) {
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


    //add mapping for list  individuals
    @GetMapping("/list")
    public String listIndividuals(Model theModel){

        //get the individuals from db
        List<Individual> theIndividual=individualService.findAll();


        //add to the spring model
        theModel.addAttribute("individuals",theIndividual);

        return "individuals/list-individuals";
    }
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){

        // create model attribute to bind form data
        Individual theEmployee =new Individual();



        theModel.addAttribute("individual",theEmployee);

        return "individuals/Add-individual";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("individualId")int theId, Model theModel){


        //get the employee from the service
        Individual theIndividual=individualService.findById(theId);
        System.out.println(theIndividual.getFirstLastName());

        // set employee in the model to prepulate the form
        theModel.addAttribute("individual", theIndividual);


        //send over to our form
        return "individuals/Update-individual";
    }

    @PostMapping("/save")
    public String saveIndividual(@ModelAttribute("individual") Individual theIndividual){

       // System.out.println("getCurrentMonthlyIncome "+theIndividual.getCurrentMonthlyIncome());
        //save the individual
        individualService.save(theIndividual);

        // use a redirect to prevent duplicate submissions
        return "redirect:/individuals/list";
    }
    @PostMapping("/update")
    public String updateIndividual(@Valid @ModelAttribute("individual") Individual theIndividual,
                                   BindingResult theBindingResult, RedirectAttributes redirectAttributes){

        //Validate if exists errors in thymeleaf
        if (theBindingResult.hasErrors()){
            return "individuals/Update-individual";
        }else{
            //save the individual
            individualService.update(theIndividual);
            // use a redirect to prevent duplicate submissions
            return "redirect:/individuals/list";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("individualId2") int theId){

        //delete the individual
        individualService.delete(theId);

        // redirect to the /individual/list
        return "redirect:/individuals/list";
    }
}
