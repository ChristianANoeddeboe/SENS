package com.example.root.sens.observers;

import java.util.Date;

public interface MainFullScreenObserver {
    void showFragment(boolean dayData, boolean goalData, Date date);
    void showDataFetchSnack();
}
