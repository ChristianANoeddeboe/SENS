package com.example.root.sens.auxiliary;

import com.example.root.sens.R;
import com.example.root.sens.dto.ActivityCategories;

public class ColorManagement {

    public int getGoalColor(ActivityCategories curr) {
        switch (curr) {
            case Søvn:
                //return Color.argb(255, 0, 150, 136);
                return R.color.restingColor;
            case Stå:
                //return Color.argb(255, 63, 81, 181);
                return R.color.standingColor;
            case Gang:
                //return Color.argb(255, 76, 175, 80);
                return R.color.walkingColor;
            case Cykling:
                //return Color.argb(255, 255, 152, 0);
                return R.color.cyclingColor;
            case Træning:
                //return Color.argb(244, 244, 67, 54);
                return R.color.exerciseColor;
            default:
                return R.color.white;
        }
    }
}
