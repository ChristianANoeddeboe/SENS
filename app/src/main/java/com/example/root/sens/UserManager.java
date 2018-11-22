package com.example.root.sens;

import com.example.root.sens.dao.IUserDao;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.User;
import java.util.Date;

public class UserManager {
    IUserDao userDao;
    private User user = null;

    public UserManager(IUserDao userDao){
        this.userDao = userDao;
    }

    public User getUserFromDb(String sensorId){
        if(user==null){
            user = userDao.getUser(sensorId);
        }
        return user;
    }

    public void createUser(User user){

    }

    public void createGoals(Goal[] goals){

    }
}
