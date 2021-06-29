package com.pixelpk.kixxmobile.User.ModelClasses;

public class ImageSliderList {

    String imageurl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public ImageSliderList(String imageurl,String id) {
        this.imageurl = imageurl;
        this.id = id;
    }

    public String getImage() {
        return imageurl;
    }

    public void setImage(String imageurl) {
        this.imageurl = imageurl;
    }
}
