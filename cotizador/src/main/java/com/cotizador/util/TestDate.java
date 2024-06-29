package com.cotizador.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestDate {
    public static void main(String[] args) {
        // having today,
        LocalDateTime beforeOfNextMonth = LocalDateTime.now();
        List<Integer> years=new ArrayList<>();
        for (int i=8; i>0;i--){
            years.add(beforeOfNextMonth.minusYears(i).getYear());
            beforeOfNextMonth.minusYears(i);
            //System.out.println( beforeOfNextMonth.minusYears(i).getYear());
        }
        for(Integer year:years){
            System.out.println(year);
        }


                // add one to the month
                //.withMonth(0);

        // then return it as formatted String





    }
}
