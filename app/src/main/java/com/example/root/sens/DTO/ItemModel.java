package com.example.root.sens.DTO;

public class ItemModel{
    private String primaryTxt;
    private int imgId;

    public ItemModel(String primaryTxt, int imgId){
        this.primaryTxt = primaryTxt;
        this.imgId = imgId;
    }
    public String getPrimaryTxt() {
        return primaryTxt;
    }

    public int getImgId() {
        return imgId;
    }
}
