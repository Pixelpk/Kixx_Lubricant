package com.pixelpk.kixxmobile.Salesman.ModelClasses;

public class ClaimsList {

    String date;
    String productName;
    String carnumber;

    public ClaimsList(String date, String productName, String carnumber) {
        this.date = date;
        this.productName = productName;
        this.carnumber = carnumber;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }
}
