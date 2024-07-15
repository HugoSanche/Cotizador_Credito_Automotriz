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



    @Value("${fixed.rate}")
    double rateFixed=0;
    @Value("${year.car}")
    int totalYears=0;
    List<Integer> yearsVehicle;



    public PaymentCalculatorController(IndividualService individualService, PaymentCalculatorService paymentCalculatorService, ChargeService chargeService,
                                       PaymentDayService paymentDayService, BrandService brandService,
                                       ModelService modelService,List<Integer> yearsVehicle

                                       ) {
        this.individualService = individualService;
        this.paymentCalculatorService = paymentCalculatorService;
        this.chargeService = chargeService;
        this.paymentDayService = paymentDayService;
        this.brandService = brandService;
        this.modelService = modelService;
        this.yearsVehicle = yearsVehicle;
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
        List<Models> theModels=modelService.findById(brandId);

        System.out.println("id "+theModels.get(0).getModelId()+" name "+theModels.get(0).getName());
        return theModels;

    }
    @GetMapping("/simulador-credito-automotriz")
    public String addPaymentCalculator(Model theModel){

        Individual individual=new Individual();
        Brands brands =new Brands();

        List<Models> listOfModels;

        //Get all brands from DB and fill to List
        List<Brands> listOfBrands=brandService.findAll();


        //Get all models from DB and fill to list
        // Inicialmente, cargar los productos de la primera categoría (si existe)
        if (!listOfBrands.isEmpty()) {
            Brands firstBrand = listOfBrands.get(0); // seleccionar la primera categoría por defecto
            listOfModels = modelService.findById(firstBrand.getBrandId());
            theModel.addAttribute("theModels", listOfModels);

        }
      //  System.out.println(individual.getPaymentCalculadors().get(0).getBrandId());
        //get years of vehicle
        yearsVehicle=getYear(totalYears);

        //Add models to view
        theModel.addAttribute("theBrands",listOfBrands);
        theModel.addAttribute("individual", individual);
        theModel.addAttribute("theYearsVehicle",yearsVehicle);

        //theModel.addAttribute("theModels",models);

        return "paymentcalculator/Add-Individual";
    }

//    @PostMapping("/save2")
//    public ModelAndView saveIndividual2(@Valid @ModelAttribute("individual") Individual theIndividual,
//                                        BindingResult theBindingResult, Model theModel){
//        BigDecimal interestPeriod=new BigDecimal(0);
//
//        if( theBindingResult.hasErrors()){
//            yearsVehicle=getYear(totalYears);
//            System.out.println("Error ");
//
//            //  redirectAttributes.addFlashAttribute("theYearsVehicle",yearsVehicle);
//            // return "redirect:/paymentcalculator/Add-PaymentCalculator";
//
//            theModel.addAttribute("attribute", "redirectWithRedirectPrefix");
//
//            return new ModelAndView("redirect:/redirectedUrl", theModel);
//           // return "paymentcalculator/Add-PaymentCalculator";
//        }
//        else {
//          //  return new RedirectView("paymentcalculator/Add-PaymentCalculator");
//            }
//    }


    @PostMapping("/saveIndividual")
    public String saveIndividual(@Valid @ModelAttribute("individual") Individual theIndividual,

                                 BindingResult theBindingResult, Model theModel){

        BigDecimal interestPeriod=new BigDecimal(0);

        if( theBindingResult.hasErrors()){

            //  redirectAttributes.addFlashAttribute("theYearsVehicle",yearsVehicle);
            // return "redirect:/paymentcalculator/Add-PaymentCalculator";
            return "paymentcalculator/Add-Individual";
        }
        else {
            //fill null values
            theIndividual.setDwellingType("NotApplies");
            theIndividual.setMaritalStatus("Unknoww");
            theIndividual.setNumberOfDependents(0);
            theIndividual.setHiringType("Normalpayroll");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

           // List<PaymentCalculator> paymentCalculatorList = new ArrayList<>();

            //Save individual in BD
            individualService.save(theIndividual);
            Brands brands =new Brands();

            List<Models> listOfModels;

            //Get all brands from DB and fill to List
            List<Brands> listOfBrands=brandService.findAll();


            //Get all models from DB and fill to list
            // Inicialmente, cargar los productos de la primera categoría (si existe)
            if (!listOfBrands.isEmpty()) {
                Brands firstBrand = listOfBrands.get(0); // seleccionar la primera categoría por defecto
                listOfModels = modelService.findById(firstBrand.getBrandId());

                //Add models to view
                theModel.addAttribute("theModels", listOfModels);
            }
            //  System.out.println(individual.getPaymentCalculadors().get(0).getBrandId());
            //get years of vehicle
            yearsVehicle=getYear(totalYears);

            PaymentCalculator paymentCalculator =new PaymentCalculator();


            //Add models to view
            theModel.addAttribute("thePaymentCalculator",paymentCalculator);
            theModel.addAttribute("theBrands",listOfBrands);
            theModel.addAttribute("theYearsVehicle",yearsVehicle);

            return "paymentcalculator/Add-PaymentCalculator";
        }
    }

    @PostMapping("/savePaymentCalculator")
    public String saveIndividual2(@Valid @ModelAttribute("thePaymentCalculator") PaymentCalculator thePaymentCalculator,
                                  @Valid @ModelAttribute("theYearsVehicle") int theYearsVehicle,
                                  BindingResult theBindingResult, Model theModel){

        BigDecimal interestPeriod=new BigDecimal(0);

        if( theBindingResult.hasErrors()){
            yearsVehicle=getYear(totalYears);
            System.out.println("Error ");

            //  redirectAttributes.addFlashAttribute("theYearsVehicle",yearsVehicle);
            // return "redirect:/paymentcalculator/Add-PaymentCalculator";
            return "paymentcalculator/Add-PaymentCalculator";
        }
        else {
            //fill null values

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            List<PaymentCalculator> paymentCalculatorList = new ArrayList<>();

            //fill entity paymentCalculator
            PaymentCalculator paymentCalculator = new PaymentCalculator(
                    date,
                    thePaymentCalculator.getYearVehicle(),
                    thePaymentCalculator.getVehiclePrice(),
                    thePaymentCalculator.getDownPayment(),
                    thePaymentCalculator.getBrandId(),
                    thePaymentCalculator.getModelId(),
                    thePaymentCalculator.getLoanTerm(),
                    1,
                    rateFixed,
                    0);
            //fill individual paymentCalculator and add paymentCalculator;
            paymentCalculatorList.add(paymentCalculator);
            // save the individual

            paymentCalculatorService.save(paymentCalculator);

            //Get calculation Value for "Comision por apertura"
            List<Charges> charges = chargeService.findByName("COMISION POR APERTURA");
            System.out.println("Charges " + charges.get(0).getCalculationValue());


            //Get Day of payment
            List<PaymentDay> paymentDay = paymentDayService.findByDayToExecute(true);
            // System.out.println(paymentDay.get(0).getPaymentDay());

            //get days
            long daysToCalculateInterest = getDays(paymentDay.get(0).getPaymentDay());

            //calculate interest of period from the initial charges
            interestPeriod = calculateInterest(paymentCalculator.calculateAmountCredit(), paymentCalculator.getRateValue(), daysToCalculateInterest);

            //add models to view
            theModel.addAttribute("thePaymentCalculator", paymentCalculator);
            theModel.addAttribute("theCharges", charges);
            theModel.addAttribute("theinterestPeriod", interestPeriod);

            return "paymentcalculator/Show-PaymentCalculator";
        }
    }



    @PostMapping("/save")
    public String saveIndividual2(@Valid @ModelAttribute("individual") Individual theIndividual,
                                 @Valid @ModelAttribute("theYearsVehicle") int theYearsVehicle,
                                 BindingResult theBindingResult, Model theModel){

        BigDecimal interestPeriod=new BigDecimal(0);

        if( theBindingResult.hasErrors()){
           yearsVehicle=getYear(totalYears);
            System.out.println("Error ");

          //  redirectAttributes.addFlashAttribute("theYearsVehicle",yearsVehicle);
           // return "redirect:/paymentcalculator/Add-PaymentCalculator";
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

            //fill entity paymentCalculator
            PaymentCalculator paymentCalculator = new PaymentCalculator(
                    date,
                    theIndividual.getPaymentCalculadors().get(0).getYearVehicle(),
                    theIndividual.getPaymentCalculadors().get(0).getVehiclePrice(),
                    theIndividual.getPaymentCalculadors().get(0).getDownPayment(),
                    theIndividual.getPaymentCalculadors().get(0).getBrandId(),
                    theIndividual.getPaymentCalculadors().get(0).getModelId(),
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
            List<Charges> charges = chargeService.findByName("COMISION POR APERTURA");
            System.out.println("Charges " + charges.get(0).getCalculationValue());


            //Get Day of payment
            List<PaymentDay> paymentDay = paymentDayService.findByDayToExecute(true);
            // System.out.println(paymentDay.get(0).getPaymentDay());

            //get days
            long daysToCalculateInterest = getDays(paymentDay.get(0).getPaymentDay());

            //calculate interest of period from the initial charges
            interestPeriod = calculateInterest(paymentCalculator.calculateAmountCredit(), paymentCalculator.getRateValue(), daysToCalculateInterest);

            //add models to view
            theModel.addAttribute("thePaymentCalculator", paymentCalculator);
            theModel.addAttribute("theCharges", charges);
            theModel.addAttribute("theinterestPeriod", interestPeriod);

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
    public BigDecimal calculateInterest(BigDecimal amountCredit, double rateFixed, long daysOfInteres){
        BigDecimal rate = new BigDecimal(rateFixed);
        BigDecimal daysOfYears = new BigDecimal(360);
        BigDecimal daysOfInt = new BigDecimal(daysOfInteres);
        BigDecimal interestPeriod=amountCredit.multiply(rate).divide(daysOfYears, RoundingMode.HALF_UP ).multiply(daysOfInt);
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













