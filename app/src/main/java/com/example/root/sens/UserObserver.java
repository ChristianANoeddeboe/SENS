package com.example.root.sens;

import com.example.root.sens.dto.User;

public interface UserObserver {
    void update(String tag, User user);
}
