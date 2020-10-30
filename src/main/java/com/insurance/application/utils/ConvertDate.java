package com.insurance.application.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {

    public static String convertDate(String stringDate) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "0000-00-00";
    }
}
