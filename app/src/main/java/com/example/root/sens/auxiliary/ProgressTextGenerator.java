package com.example.root.sens.auxiliary;

import java.util.regex.Pattern;

public class ProgressTextGenerator {
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
