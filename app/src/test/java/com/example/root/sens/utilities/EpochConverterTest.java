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
}