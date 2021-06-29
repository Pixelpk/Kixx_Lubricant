package com.pixelpk.kixxmobile.User.ModelClasses;

import android.graphics.drawable.Drawable;

public class CarRecommendationlistModelClass {

    int image;
    String name;
    String distance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public CarRecommendationlistModelClass(int image, String name, String distance,String id) {
        this.image = image;
        this.name = name;
        this.distance = distance;
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
