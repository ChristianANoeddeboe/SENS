package com.example.root.sens.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalTest {

    Animal animal;

    @Before
    public void setUp() throws Exception {
        animal = new Animal(50);
    }

    @Test
    public void talk() {
        String[] speaklines = animal.getSpeaklines();
        String actual = animal.talk();
        String expected = speaklines[5];
        assertEquals("Mood was: "+animal.getMood(), actual, expected);
    }

    @Test
    public void talk51() {
        String[] speaklines = animal.getSpeaklines();
        animal.setMood(51);
        String actual = animal.talk();
        String expected = speaklines[5];
        assertEquals(actual, expected);
    }
}