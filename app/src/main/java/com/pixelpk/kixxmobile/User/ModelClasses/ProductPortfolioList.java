package com.pixelpk.kixxmobile.User.ModelClasses;

public class ProductPortfolioList {

    String title;
    String message;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String price;
    int image;

    public ProductPortfolioList(String title, String message, int image,String price) {
        this.title = title;
        this.message = message;
        this.image = image;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
