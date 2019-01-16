package com.example.root.sens.dao.interfaces;

public interface DALUser extends  IUserDao{
    boolean isUser(int sensorID); // IS this ID already known, aka there is a user in Realm
    
}
