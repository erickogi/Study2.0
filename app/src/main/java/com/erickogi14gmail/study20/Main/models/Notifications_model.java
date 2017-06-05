package com.erickogi14gmail.study20.Main.models;

/**
 * Created by kimani kogi on 5/28/2017.
 */

public class Notifications_model {

    private int notifications_id;
    private String notifications_title;
    private String notifications_description;
    private String notification_date;
    private int notification_status;
    private String notification_channel;

    public Notifications_model() {
    }

    public Notifications_model(int notifications_id, String notifications_title, String notifications_description, String notification_date, int notification_status, String notification_channel) {
        this.notifications_id = notifications_id;
        this.notifications_title = notifications_title;
        this.notifications_description = notifications_description;
        this.notification_date = notification_date;
        this.notification_status = notification_status;
        this.notification_channel = notification_channel;
    }

    public String getNotification_channel() {
        return notification_channel;
    }

    public void setNotification_channel(String notification_channel) {
        this.notification_channel = notification_channel;
    }

    public int getNotifications_id() {
        return notifications_id;
    }

    public void setNotifications_id(int notifications_id) {
        this.notifications_id = notifications_id;
    }

    public String getNotifications_title() {
        return notifications_title;
    }

    public void setNotifications_title(String notifications_title) {
        this.notifications_title = notifications_title;
    }

    public String getNotifications_description() {
        return notifications_description;
    }

    public void setNotifications_description(String notifications_description) {
        this.notifications_description = notifications_description;
    }

    public String getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(String notification_date) {
        this.notification_date = notification_date;
    }

    public int getNotification_status() {
        return notification_status;
    }

    public void setNotification_status(int notification_status) {
        this.notification_status = notification_status;
    }
}
