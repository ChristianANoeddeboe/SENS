package com.example.root.sens.auxiliary;


import com.example.root.sens.R;
import com.example.root.sens.ActivityCategories;

public class ResourceManagement {
    /**
     * Used to get the goal colour for an activity category
     * @param curr
     * @return
     */
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
            case Skridt:
                return R.color.stepsColor;
            default:
                return R.color.white;
        }
    }

    /**
     * Used to get the right icon for a activity category
     * @param currentActivity
     * @return
     */
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
            case Skridt:
                return R.mipmap.icon_steps;
            default:
                return R.drawable.blue_button;
        }
    }
}
