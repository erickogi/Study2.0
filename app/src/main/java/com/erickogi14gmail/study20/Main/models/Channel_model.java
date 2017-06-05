package com.erickogi14gmail.study20.Main.models;

/**
 * Created by kimani kogi on 5/28/2017.
 */

public class Channel_model {
    private int notification_c_id;
    private String notification_c_name;
    private String notification_c_desc;
    private String notification_c_color;

    public Channel_model() {
    }

    public Channel_model(int notification_c_id, String notification_c_name, String notification_c_desc, String notification_c_color) {
        this.notification_c_id = notification_c_id;
        this.notification_c_name = notification_c_name;
        this.notification_c_desc = notification_c_desc;
        this.notification_c_color = notification_c_color;
    }

    public int getNotification_c_id() {
        return notification_c_id;
    }

    public void setNotification_c_id(int notification_c_id) {
        this.notification_c_id = notification_c_id;
    }

    public String getNotification_c_name() {
        return notification_c_name;
    }

    public void setNotification_c_name(String notification_c_name) {
        this.notification_c_name = notification_c_name;
    }

    public String getNotification_c_desc() {
        return notification_c_desc;
    }

    public void setNotification_c_desc(String notification_c_desc) {
        this.notification_c_desc = notification_c_desc;
    }

    public String getNotification_c_color() {
        return notification_c_color;
    }

    public void setNotification_c_color(String notification_c_color) {
        this.notification_c_color = notification_c_color;
    }
}
