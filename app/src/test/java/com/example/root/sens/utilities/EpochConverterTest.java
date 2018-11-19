package com.example.root.sens.utilities;

import org.junit.Test;

import static org.junit.Assert.*;

public class EpochConverterTest {

    @Test
    public void convertDate() {
        long expected = 1542585600000L;
        long actual;
        actual = EpochConverter.getInstance().convertDate(19, 11, 2018);
        assertEquals(expected, actual);
    }

    @Test
    public void setTimeofday() {
        String expected = "06:47:04.000";
        EpochConverter.getInstance().setTimeofday(6,47,4);
        String actual = EpochConverter.getInstance().getTimeofday();
        assertEquals(expected, actual);
    }
}