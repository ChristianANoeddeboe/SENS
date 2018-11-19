package com.example.root.sens.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.text.DateFormatSymbols;
import java.util.Date;

public class EpochConverter {

    private static EpochConverter epoch;


    private EpochConverter() {}

    public static EpochConverter getInstance() {
        if(epoch == null) {
            epoch = new EpochConverter();
        }
        return epoch;
    }

    private String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

    public long convertDate(int day, int month, int year) {
        String str = getMonth(month)+" "+day+" "+year+" 00:00:00.000 GMT";
        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");
        Date date = null;
        try {
            date = df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

}
