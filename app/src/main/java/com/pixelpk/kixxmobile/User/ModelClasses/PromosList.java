package com.pixelpk.kixxmobile.User.ModelClasses;

public class PromosList {

    String url;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getImageid() {
        return url;
    }

    public void setImageid(String url) {
        this.url = url;
    }



    public PromosList(String url,String id) {

        this.url = url;
        this.id = id;

    }



}
