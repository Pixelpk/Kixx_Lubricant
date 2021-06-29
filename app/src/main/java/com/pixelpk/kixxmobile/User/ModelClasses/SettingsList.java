package com.pixelpk.kixxmobile.User.ModelClasses;

public class SettingsList {

    int imageid;
    String description;

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SettingsList(int imageid,String description) {

        this.imageid = imageid;
        this.description = description;

    }



}
