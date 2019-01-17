package com.example.root.sens.auxiliary;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.managers.IUserManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DayDataGoalMapper {
    private Map<ActivityCategories, Integer> goalMap = new HashMap();
    private Map<ActivityCategories, Float> dataMap = new HashMap();
    private IUserManager userManager;
    private Date date = null;

    public DayDataGoalMapper(IUserManager userManager, Date date) {
        this.userManager = userManager;
        this.date = date;
        loadData();
    }

    public DayDataGoalMapper(IUserManager userManager) {
        this.userManager = userManager;
        date = new Date();
        loadData();
    }

    private void loadData() {
        goalMap = userManager.getGoals(date);
        dataMap = userManager.getDayData(date);
    }

    public Map<ActivityCategories, Integer> getGoalMap() {
        return goalMap;
    }

    public Map<ActivityCategories, Float> getDataMap() {
        return dataMap;
    }
}
