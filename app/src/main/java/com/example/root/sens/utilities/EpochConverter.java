package com.example.root.sens.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.text.DateFormatSymbols;
import java.util.Date;

public class EpochConverter {

    private static EpochConverter epoch;
    private String timeofday;

    private EpochConverter() {
        timeofday = "00:00:00.000";
    }

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
        String str = getMonth(month)+" "+day+" "+year+" "+timeofday+" GMT";
        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");
        Date date = null;
        try {
            date = df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public String getTimeofday() {
        return timeofday;
    }

    public void setTimeofday(int hour, int minute, int second) {
        String txthour = String.format("%02d", hour);
        String txtminute = String.format("%02d", minute);
        String txtsecond = String.format("%02d", second);
        timeofday = txthour+":"+txtminute+":"+txtsecond+".000";
    }
}
