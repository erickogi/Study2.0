package com.erickogi14gmail.study20.Main.models;

import java.io.Serializable;

/**
 * Created by kimani kogi on 5/19/2017.
 */

public class Events_model implements Serializable {

    //    `erickogi_db`.`study_events`
    private int id;
    private String event_title;
    private String event_price;
    private String event_description;
    private String event_start;
    private String event_end;
    private String event_general_location;
    private String event_specific_location;
    private String event_image;
    private String event_by;
    private String event_published_on;
    private String event_published_by;
    private String event_lat;
    private String event_long;

    public Events_model() {
    }

    public Events_model(int id, String event_title, String event_price, String event_description, String event_start, String event_end,
                        String event_general_location, String event_specific_location, String event_image, String event_by,
                        String event_published_on, String event_published_by, String event_lat, String event_long) {
        this.id = id;
        this.event_title = event_title;
        this.event_price = event_price;
        this.event_description = event_description;
        this.event_start = event_start;
        this.event_end = event_end;
        this.event_general_location = event_general_location;
        this.event_specific_location = event_specific_location;
        this.event_image = event_image;
        this.event_by = event_by;
        this.event_published_on = event_published_on;
        this.event_published_by = event_published_by;
        this.event_lat = event_lat;
        this.event_long = event_long;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_price() {
        return event_price;
    }

    public void setEvent_price(String event_price) {
        this.event_price = event_price;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getEvent_start() {
        return event_start;
    }

    public void setEvent_start(String event_start) {
        this.event_start = event_start;
    }

    public String getEvent_end() {
        return event_end;
    }

    public void setEvent_end(String event_end) {
        this.event_end = event_end;
    }

    public String getEvent_specific_location() {
        return event_specific_location;
    }

    public void setEvent_specific_location(String event_specific_location) {
        this.event_specific_location = event_specific_location;
    }

    public String getEvent_image() {
        return event_image;
    }

    public void setEvent_image(String event_image) {
        this.event_image = event_image;
    }

    public String getEvent_published_on() {
        return event_published_on;
    }

    public void setEvent_published_on(String event_published_on) {
        this.event_published_on = event_published_on;
    }

    public String getEvent_published_by() {
        return event_published_by;
    }

    public void setEvent_published_by(String event_published_by) {
        this.event_published_by = event_published_by;
    }

    public String getEvent_lat() {
        return event_lat;
    }

    public void setEvent_lat(String event_lat) {
        this.event_lat = event_lat;
    }

    public String getEvent_long() {
        return event_long;
    }

    public void setEvent_long(String event_long) {
        this.event_long = event_long;
    }

    public String getEvent_general_location() {
        return event_general_location;
    }

    public void setEvent_general_location(String event_general_location) {
        this.event_general_location = event_general_location;
    }

    public String getEvent_by() {
        return event_by;
    }

    public void setEvent_by(String event_by) {
        this.event_by = event_by;
    }
}

