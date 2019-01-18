package com.example.root.sens.observers;

import java.util.Date;

public interface MainFullScreenObserver {
    void showFragment(boolean dataFound, Date date);
    void showDataFetchSnack();
}
