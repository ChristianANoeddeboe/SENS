package com.example.root.sens.auxiliary;


import com.example.root.sens.R;
import com.example.root.sens.dto.ActivityCategories;

public class ResourceManagement {

    public int getGoalColor(ActivityCategories curr) {
        switch (curr) {
            case Søvn:
                return R.color.restingColor;
            case Stå:
                return R.color.standingColor;
            case Gang:
                return R.color.walkingColor;
            case Cykling:
                return R.color.cyclingColor;
            case Træning:
                return R.color.exerciseColor;
            default:
                return R.color.white;
        }
    }

    public int generateIcons(ActivityCategories currentActivity) {
        switch (currentActivity) {
            case Søvn:
                return R.mipmap.icon_resting;
            case Stå:
                return R.mipmap.icon_standing;
            case Gang:
                return R.mipmap.icon_walking;
            case Cykling:
                return R.mipmap.icon_cycling;
            case Træning:
                return R.mipmap.icon_exercise;
            default:
                return R.drawable.blue_button;
        }
    }
}
