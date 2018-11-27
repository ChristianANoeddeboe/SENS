package com.example.root.sens.controllers;

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

}
