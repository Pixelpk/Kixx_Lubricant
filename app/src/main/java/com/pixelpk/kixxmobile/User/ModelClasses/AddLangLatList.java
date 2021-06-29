package com.pixelpk.kixxmobile.User.ModelClasses;

import com.google.android.gms.maps.model.LatLng;

public class AddLangLatList {

    String id;
    String shopname;
    String shophone;
    LatLng latLng;

    public LatLng getOrigin() {
        return origin;
    }

    public void setOrigin(LatLng origin) {
        this.origin = origin;
    }

    LatLng origin;

    public AddLangLatList(String id, String shopname, String shophone, LatLng origin, LatLng latLng) {
        this.id = id;
        this.shopname = shopname;
        this.shophone = shophone;
        this.latLng = latLng;
        this.origin = origin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShophone() {
        return shophone;
    }

    public void setShophone(String shophone) {
        this.shophone = shophone;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }





}
