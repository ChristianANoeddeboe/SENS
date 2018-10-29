package com.example.root.sens;

public class mål {
    String titel;
    int progress;
    int maxProgress;
    int minProgress;
    public mål(String titel, int progress) {
        this.titel = titel;
        this.progress = progress;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public int getMinProgress() {
        return minProgress;
    }

    public void setMinProgress(int minProgress) {
        this.minProgress = minProgress;
    }
}
