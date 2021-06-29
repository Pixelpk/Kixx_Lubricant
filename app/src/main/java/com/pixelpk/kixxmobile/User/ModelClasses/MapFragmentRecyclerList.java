package com.pixelpk.kixxmobile.User.ModelClasses;

import com.google.android.gms.maps.model.LatLng;

public class MapFragmentRecyclerList {

    int imageid;
    String title,area;
    String shopname;
    String shopemail;
    String imageurl;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    LatLng latLng;

    public LatLng getOrigin() {
        return origin;
    }

    public void setOrigin(LatLng origin) {
        this.origin = origin;
    }

    LatLng origin;

    public LatLng getLatlngdestin() {
        return latlngdestin;
    }

    public void setLatlngdestin(LatLng latlngdestin) {
        this.latlngdestin = latlngdestin;
    }

    LatLng latlngdestin;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }



    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }



    public MapFragmentRecyclerList(int imageid,String title,String area,LatLng latLng,LatLng latlngdestin,String imageurl) {

        this.imageid = imageid;
        this.title = title;
        this.area = area;
        this.latLng = latLng;
        this.latlngdestin = latlngdestin;
        this.origin = origin;
        this.imageurl = imageurl;

    }



}
