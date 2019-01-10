package com.example.root.sens.auxiliary;

import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Record;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DayDataGoalMapper {
    private Map<String, Integer> goalMap = new HashMap();
    private Map<String, Float> dataMap = new HashMap();
    private UserDAO userDAO;
    private Date date = null;

    public DayDataGoalMapper(UserDAO userDAO, Date date) {
        this.userDAO = this.userDAO;
        this.date = date;
        loadData();
    }

    public DayDataGoalMapper(UserDAO user) {
        this.userDAO = user;
        loadData();
    }

    private void loadData() {
        GoalHistory goals;
        DayData dayData;
        if(date == null){
            goals = userDAO.getUserLoggedIn().getGoals().get(0);
            dayData = userDAO.getUserLoggedIn().getDayData().get(0);
        }else {
            goals = userDAO.getGoalSpecificDate(date);
            dayData = userDAO.getDataSpecificDate(date);
        }


        for (Goal goal : goals.getGoals()) {
            goalMap.put(String.valueOf(goal.getType()), goal.getValue());
        }

        for (Record record : dayData.getRecords()) {
            dataMap.put(String.valueOf(record.getType()), record.getValue());
        }
    }

    public Map<String, Integer> getGoalMap() {
        return goalMap;
    }

    public Map<String, Float> getDataMap() {
        return dataMap;
    }
}
