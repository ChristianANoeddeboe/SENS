package com.example.root.sens.dao;

import com.example.root.sens.dto.User;

public interface IUserDao {
    void setUserLoggedIn(User user);

    void removeUserLoggedIn(User user);

    void saveUser(User user);
    User getUserLoggedIn();
    User getUser(String sensorId);
}
