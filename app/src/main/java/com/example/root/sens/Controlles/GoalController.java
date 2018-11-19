package com.example.root.sens.Controlles;

import android.util.Log;

import java.util.Calendar;

public class GoalController {
    /**
     * This method will set the progress of the goals.
     */
    static GoalController instance;

    private GoalController(){

    }

    public GoalController getInstance(){
        if(instance==null){
            instance = new GoalController();
        }
        return instance;
    }

    public void updateDayProgress(){
        Log.d("Test",Calendar.getInstance().toString());
    }
}
