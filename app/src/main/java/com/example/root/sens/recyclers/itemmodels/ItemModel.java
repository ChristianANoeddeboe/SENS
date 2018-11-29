package com.example.root.sens.recyclers.itemmodels;

public class ItemModel{
    private String primaryText;
    private String secondaryText;
    private int imgId;

    public ItemModel(String primaryText, int imgId){
        this.primaryText = primaryText;
        this.secondaryText = null;
        this.imgId = imgId;
    }

    public ItemModel(String primaryText, String secondaryText, int imgId){
        this.primaryText = primaryText;
        this.secondaryText = secondaryText;
        this.imgId = imgId;
    }

    public String getPrimaryText() {
        return primaryText;
    }

    public int getImgId() {
        return imgId;
    }

    public String getSecondaryText() {
        return secondaryText;
    }
}
