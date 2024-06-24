package com.cotizador.util;

import com.cotizador.service.IndividualService;
import org.springframework.stereotype.Controller;

@Controller
//Esta clase obtiene el porcentaje de calculo de  la comision de apertura
public class CalculationValue {
IndividualService individualService;


    public CalculationValue(IndividualService individualService) {
        this.individualService = individualService;
    }

    public CalculationValue(){

    }
    //En base al nombre del cargo obtiene el monto del porcentaje de la comision por apertura
    //Recibe como parametro el nombre del cargo a buscar
    public  double getCalculationValue(String nameCharge){

        System.out.println("getCalculationValue");
        //get charge from the name of charge
        //List<Charges> charges=chargeService.findByName(nameCharge);

        //Charges charges=chargeService.findById(1);
    //    Individual individual=individualService.findById(1);
        //System.out.println("Charges "+charges.getName());

     //   System.out.println(individual.getEmail());
       //Charges myCharge= charges.get(0);
       double comisionPorApertura=0.75;
       return comisionPorApertura;
    }

}
