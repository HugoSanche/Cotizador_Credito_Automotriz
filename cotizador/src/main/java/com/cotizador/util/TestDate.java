package com.cotizador.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestDate {
    public static void main(String[] args) {
        // get a reference to today
        LocalDate today = LocalDate.now();
        // having today,
        LocalDate firstOfNextMonth = today
                // add one to the month
                .withMonth(today.getMonthValue() + 1)
                // and take the first day of that month
                .withDayOfMonth(15);
        // then return it as formatted String
        System.out.println( (firstOfNextMonth.format(DateTimeFormatter.ISO_LOCAL_DATE)));

    }
}
