package com.pixelpk.kixxmobile.User.ModelClasses;

public class CarDetailsList {

    String date;
    String odometer;
    String next_odometer;

    public String getNextdate() {
        return nextdate;
    }

    public void setNextdate(String nextdate) {
        this.nextdate = nextdate;
    }

    String nextdate;

    public CarDetailsList(String date, String odometer, String next_odometer,String nextdate) {
        this.date = date;
        this.odometer = odometer;
        this.next_odometer = next_odometer;
        this.nextdate = nextdate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getNext_odometer() {
        return next_odometer;
    }

    public void setNext_odometer(String next_odometer) {
        this.next_odometer = next_odometer;
    }




}
