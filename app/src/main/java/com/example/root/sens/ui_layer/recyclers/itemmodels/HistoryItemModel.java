package com.example.root.sens.ui_layer.recyclers.itemmodels;

public class HistoryItemModel {
    private String textViewTitle;
    private String textViewDate;
    private String textViewDescription;
    private int imageId;

    public HistoryItemModel(String textViewTitle, String textViewDate, String textViewDescription, int imageId){
        this.textViewTitle = textViewTitle;
        this.textViewDate = textViewDate;
        this.textViewDescription = textViewDescription;
        this.imageId = imageId;
    }

    public String getTextViewTitle() {
        return textViewTitle;
    }

    public void setTextViewTitle(String textViewTitle) {
        this.textViewTitle = textViewTitle;
    }

    public String getTextViewDate() {
        return textViewDate;
    }

    public void setTextViewDate(String textViewDate) {
        this.textViewDate = textViewDate;
    }

    public String getTextViewDescription() {
        return textViewDescription;
    }

    public void setTextViewDescription(String textViewDescription) {
        this.textViewDescription = textViewDescription;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
