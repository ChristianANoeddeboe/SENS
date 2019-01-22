package com.example.root.sens.view_layer.recyclers.viewholder;

import java.util.Date;

public class MonthSingleton {
    private Date currentMonth;
    private static MonthSingleton instance;
    private MonthSingleton(){
    }
    public static MonthSingleton getInstance(){
        if(instance == null){
            instance = new MonthSingleton();
            instance.setCurrentMonth(new Date());
        }
        return instance;
    }

    public Date getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(Date currentMonth) {
        this.currentMonth = currentMonth;
    }

}
