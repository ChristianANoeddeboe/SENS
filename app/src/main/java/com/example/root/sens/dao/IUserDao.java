package com.example.root.sens.dao;

import com.example.root.sens.dto.User;

public interface IUserDao {
    //public User getUser(String userId);
    public User getUser(String sensorId);
    public User saveUser(User user);
}
