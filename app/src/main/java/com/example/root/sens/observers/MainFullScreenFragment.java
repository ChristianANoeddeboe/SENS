package com.example.root.sens.observers;

import com.example.root.sens.dto.sensresponse.DayGoalDTO;

import java.util.List;

public interface MainFullScreenFragment {
    void showFragment(List<DayGoalDTO> dataList);
}
