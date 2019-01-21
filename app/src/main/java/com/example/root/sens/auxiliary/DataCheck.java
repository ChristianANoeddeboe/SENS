package com.example.root.sens.auxiliary;

public class DataCheck {
    public static boolean checkIntOverflow(int lastDigit, int i){
        if(lastDigit > 0 && i > Integer.MAX_VALUE-lastDigit) return true;
        return false;
    }
}
