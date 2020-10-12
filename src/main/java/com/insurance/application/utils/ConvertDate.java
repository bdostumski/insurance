package com.insurance.application.utils;

import com.insurance.application.exceptions.ParserException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {

    public static String convertDate(String stringDate) throws ParseException {

            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
            return simpleDateFormat.format(date);
    }
}
