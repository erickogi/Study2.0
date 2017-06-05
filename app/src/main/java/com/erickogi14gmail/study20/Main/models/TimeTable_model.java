package com.erickogi14gmail.study20.Main.models;

/**
 * Created by kimani kogi on 5/30/2017.
 */

public class TimeTable_model {

    private int timetable_id;
    private String timetable_title;
    private String timetable_course_name;
    private String timetable_course_year;
    private String timetable_published_by;
    private String timetable_published_on;
    private String timetable_content;

    public TimeTable_model() {
    }

    public TimeTable_model(int timetable_id, String timetable_title, String timetable_course_name, String timetable_course_year,
                           String timetable_published_by, String timetable_published_on, String timetable_content) {
        this.timetable_id = timetable_id;
        this.timetable_title = timetable_title;
        this.timetable_course_name = timetable_course_name;
        this.timetable_course_year = timetable_course_year;
        this.timetable_published_by = timetable_published_by;
        this.timetable_published_on = timetable_published_on;
        this.timetable_content = timetable_content;
    }

    public int getTimetable_id() {
        return timetable_id;
    }

    public void setTimetable_id(int timetable_id) {
        this.timetable_id = timetable_id;
    }

    public String getTimetable_title() {
        return timetable_title;
    }

    public void setTimetable_title(String timetable_title) {
        this.timetable_title = timetable_title;
    }

    public String getTimetable_course_name() {
        return timetable_course_name;
    }

    public void setTimetable_course_name(String timetable_course_name) {
        this.timetable_course_name = timetable_course_name;
    }

    public String getTimetable_course_year() {
        return timetable_course_year;
    }

    public void setTimetable_course_year(String timetable_course_year) {
        this.timetable_course_year = timetable_course_year;
    }

    public String getTimetable_published_by() {
        return timetable_published_by;
    }

    public void setTimetable_published_by(String timetable_published_by) {
        this.timetable_published_by = timetable_published_by;
    }

    public String getTimetable_published_on() {
        return timetable_published_on;
    }

    public void setTimetable_published_on(String timetable_published_on) {
        this.timetable_published_on = timetable_published_on;
    }

    public String getTimetable_content() {
        return timetable_content;
    }

    public void setTimetable_content(String timetable_content) {
        this.timetable_content = timetable_content;
    }
}
