package com.example.root.sens.dao;

import com.example.root.sens.dto.User;

public interface IUserDao {
    void createUser(User user);

    void setUserLoggedIn(User user);

    void removeUserLoggedIn(User user);

    void saveUser(User user);

    User getUser(String sensorId);
}
