package com.pixelpk.kixxmobile.User.ModelClasses;

public class CarStatus {

    String status;

    public CarStatus(String status, String car_id,String car_number) {

        this.status = status;
        this.car_id = car_id;
        this.car_number = car_number;
    }

    String car_id;

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    String car_number ;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }
}
