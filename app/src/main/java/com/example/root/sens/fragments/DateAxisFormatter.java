package com.example.root.sens.fragments;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateAxisFormatter implements IAxisValueFormatter {

    private SimpleDateFormat mFormat;

    public DateAxisFormatter() {
        // format values to 1 decimal digit
        mFormat = new SimpleDateFormat("E 'den' DD'.", new Locale("da"));
    }

    @Override
    public String getFormattedValue(Date value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return mFormat.format(value);
    }

    /** this is only needed if numbers are returned, else return 0 */
    @Override
    public int getDecimalDigits() { return 1; }
}