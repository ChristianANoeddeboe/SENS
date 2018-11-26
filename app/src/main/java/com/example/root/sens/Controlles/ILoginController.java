package com.example.root.sens.Controlles;

import com.example.root.sens.dto.Goal;

import java.util.Date;

public interface ILoginController {
    public boolean login(String sensorId);
    public void userDataConfirm(String name, Date birthday);
    public void userGoalsConfirm(Goal[] goals);
    public void finalConfirm();
}
