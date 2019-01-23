package com.example.root.sens.auxiliary;

import java.util.regex.Pattern;

public class ProgressTextGenerator {
    /**
     * Method used to generate the progess text i.e. 0 hours / 4 hours
     * @param text progress in minutes.
     * @return A formatted string such as '4 hours'
     */
    public String generateProgressText(String text){
        String result = null;
        if(Pattern.matches("[0-9]+", text)){
            int progress = Integer.parseInt(text);
            int hours = progress/60;
            int minuttes = progress%60;
            result = ""+hours+" timer & "+minuttes+" minutter";
        }
        else result = text;
        return result;

    }
}
