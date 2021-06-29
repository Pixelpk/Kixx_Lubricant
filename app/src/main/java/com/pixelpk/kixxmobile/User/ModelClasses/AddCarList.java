package com.pixelpk.kixxmobile.User.ModelClasses;

public class AddCarList {

    String Car_Number;
    String Car_Manufacturer;
    String Car_Brand;
    String Car_Model;
    String Car_id;
    String odometer;
    String daily_mileage;
    String year_of_manufacture;
    String engine_type;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    String cid;

    public AddCarList(String car_Number, String car_Manufacturer, String car_Brand, String car_Model, String Car_id,String odometer,String daily_mileage,String year_of_manufacture,String engine_type,String cid) {
        Car_Number = car_Number;
        Car_Manufacturer = car_Manufacturer;
        Car_Brand = car_Brand;
        Car_Model = car_Model;
        this.Car_id = Car_id;
        this.odometer = odometer;
        this.daily_mileage = daily_mileage;
        this.year_of_manufacture = year_of_manufacture;
        this.engine_type = engine_type;
        this.cid = cid;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getDaily_mileage() {
        return daily_mileage;
    }

    public void setDaily_mileage(String daily_mileage) {
        this.daily_mileage = daily_mileage;
    }

    public String getYear_of_manufacture() {
        return year_of_manufacture;
    }

    public void setYear_of_manufacture(String year_of_manufacture) {
        this.year_of_manufacture = year_of_manufacture;
    }

    public String getEngine_type() {
        return engine_type;
    }

    public void setEngine_type(String engine_type) {
        this.engine_type = engine_type;
    }



    public String getCar_id() {
        return Car_id;
    }

    public void setCar_id(String car_id) {
        Car_id = car_id;
    }


    public String getCar_Number() {
        return Car_Number;
    }

    public void setCar_Number(String car_Number) {
        Car_Number = car_Number;
    }

    public String getCar_Manufacturer() {
        return Car_Manufacturer;
    }

    public void setCar_Manufacturer(String car_Manufacturer) {
        Car_Manufacturer = car_Manufacturer;
    }

    public String getCar_Brand() {
        return Car_Brand;
    }

    public void setCar_Brand(String car_Brand) {
        Car_Brand = car_Brand;
    }

    public String getCar_Model() {
        return Car_Model;
    }

    public void setCar_Model(String car_Model) {
        Car_Model = car_Model;
    }
}
