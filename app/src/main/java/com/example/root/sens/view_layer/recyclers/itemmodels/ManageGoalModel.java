package com.example.root.sens.view_layer.recyclers.itemmodels;

public class ManageGoalModel {
    private String textHeader;
    private String textTotal;
    private final int SEEKBAR_MAXCONST = 24*60;
    private int max;
    private int counter;
    private int[] oldValues;
    private boolean changed = false;
    private int progess;

    public ManageGoalModel(String textHeader, String textTotal, int counter, int[] oldValues, boolean changed, int progress) {
        this.textHeader = textHeader;
        this.textTotal = textTotal;
        this.counter = counter;
        this.oldValues = oldValues;
        this.changed = changed;
        this.progess = progress;
        this.max = SEEKBAR_MAXCONST;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgess(int progess) {
        this.progess = progess;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getProgess() {
        return progess;
    }

    public int[] getOldValues() {
        return oldValues;
    }

    public int getCounter() {
        return counter;
    }

    public int getSEEKBAR_MAXCONST() {
        return SEEKBAR_MAXCONST;
    }

    public String getTextHeader() {
        return textHeader;
    }

    public void setTextHeader(String textHeader) {
        this.textHeader = textHeader;
    }

    public String getTextTotal() {
        return textTotal;
    }

    public void setTextTotal(String textTotal) {
        this.textTotal = textTotal;
    }

    public boolean isChanged() {
        return changed;
    }
}
