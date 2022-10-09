package com.example.firebasevideostreaming.model;

public class videoFileModel {
    private String name,vurl;

    public videoFileModel() {

    }

    public videoFileModel(String name, String vurl) {
        this.name = name;
        this.vurl = vurl;
    }

    public String getName() {
        return name;
    }

    public String getVurl() {
        return vurl;
    }
}
