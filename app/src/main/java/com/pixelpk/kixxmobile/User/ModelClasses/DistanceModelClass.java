package com.pixelpk.kixxmobile.User.ModelClasses;

import com.google.android.gms.maps.model.LatLng;

public class DistanceModelClass  {

    String imageid;
    String title;
    LatLng area;
    String shopname;
    String shopemail;
    String imageurl;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public LatLng getOrigin() {
        return origin;
    }

    public void setOrigin(LatLng origin) {
        this.origin = origin;
    }

    LatLng origin;
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    double distance;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    LatLng latLng;

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShopemail() {
        return shopemail;
    }

    public void setShopemail(String shopemail) {
        this.shopemail = shopemail;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getArea() {
        return area;
    }

    public void setArea(LatLng area) {
        this.area = area;
    }



    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }



    public DistanceModelClass(String imageid,String title,LatLng origin,LatLng area,double distance,String imageurl) {

        this.imageid = imageid;
        this.title = title;
        this.area = area;
        this.distance = distance;
        this.origin = origin;
        this.imageurl = imageurl;

    }


}
