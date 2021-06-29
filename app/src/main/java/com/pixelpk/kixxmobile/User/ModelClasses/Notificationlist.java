package com.pixelpk.kixxmobile.User.ModelClasses;

public class Notificationlist {

    String notification_desc;
    String notification_time;
    String notification_title;

    public String getNotification_title() {
        return notification_title;
    }

    public void setNotification_title(String notification_title) {
        this.notification_title = notification_title;
    }



    public String getNotification_desc() {
        return notification_desc;
    }

    public void setNotification_desc(String notification_desc) {
        this.notification_desc = notification_desc;
    }

    public String getNotification_time() {
        return notification_time;
    }

    public void setNotification_time(String notification_time) {
        this.notification_time = notification_time;
    }



    public Notificationlist( String notification_desc, String notification_time,String notification_title) {

        this.notification_desc = notification_desc;
        this.notification_time = notification_time;
        this.notification_title = notification_title;
    }



}
