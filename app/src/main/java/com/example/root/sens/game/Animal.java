package com.example.root.sens.game;

public class Animal {

    private int mood;
    private String[] speaklines = {
            "I have become... God.",                                // 0 - 10
            "I am execellent! Couldn't do it better if i tried!",   // 11 - 20
            "I am very good, keep up the activity.",                // 21 - 30
            "I am good, just need to go out a bit more.",           // 31 - 40
            "I am okay. Would be nice with more activity.",         // 41 - 50
            "I could do with more activity.",                       // 51 - 60
            "Don't feel so good. I really need more activity.",     // 61 - 70
            "Please give me more activity..",                       // 71 - 80
            "I haven't been out in days...",                        // 81 - 90
            "... Need ... more ... activity ..."                    // 91 - 100
    };

    public Animal(int mood) {
        this.mood = mood;
    }

    public String talk() {
        int temp = (int) Math.floor(mood/10.0);
        return speaklines[temp];
    }

    public void setMood(int mood) {
        if(mood > 100) {
            this.mood = 100;
            return;
        }
        if(mood < 0) {
            this.mood = 0;
            return;
        }
        this.mood = mood;
    }

    public int getMood() {
        return mood;
    }

    public String[] getSpeaklines() {
        return speaklines;
    }
}
