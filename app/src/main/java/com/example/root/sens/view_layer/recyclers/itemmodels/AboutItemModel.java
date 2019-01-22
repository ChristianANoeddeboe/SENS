package com.example.root.sens.view_layer.recyclers.itemmodels;


public class AboutItemModel {
    private String name;
    private String studentNumber;
    private int img;

    public AboutItemModel(String name, String studentNumber, int img) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
