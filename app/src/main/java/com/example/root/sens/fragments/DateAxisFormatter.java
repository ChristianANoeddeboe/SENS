package com.example.root.sens.fragments;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateAxisFormatter implements IAxisValueFormatter {

    private SimpleDateFormat mFormat;
    private long dateInMilis;

    public DateAxisFormatter() {
        mFormat = new SimpleDateFormat("d. MMM", new Locale("da"));
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Date date = new Date(Float.valueOf(dateInMilis).longValue());
        return mFormat.format(date);
    }

    public void setDateInMilis(long dateInMilis){
        this.dateInMilis = dateInMilis;
    }

}