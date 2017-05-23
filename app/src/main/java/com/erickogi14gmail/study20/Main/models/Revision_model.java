package com.erickogi14gmail.study20.Main.models;

/**
 * Created by kimani kogi on 5/22/2017.
 */

public class Revision_model {

    private int id;
    private String revision_title;
    private String revision_course_code;
    private String revision_course_name;
    private String revision_date;
    private String revision_uploaded_by;
    private String revision_content;
    private String revision_uploaded_on;

    public Revision_model() {
    }

    public Revision_model(int id, String revision_title, String revision_course_code,
                          String revision_course_name, String revision_date, String revision_uploaded_by, String revision_content,
                          String revision_uploaded_on) {
        this.id = id;
        this.revision_title = revision_title;
        this.revision_course_code = revision_course_code;
        this.revision_course_name = revision_course_name;
        this.revision_date = revision_date;
        this.revision_uploaded_by = revision_uploaded_by;
        this.revision_content = revision_content;
        this.revision_uploaded_on = revision_uploaded_on;
    }

    public String getRevision_content() {
        return revision_content;
    }

    public void setRevision_content(String revision_content) {
        this.revision_content = revision_content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRevision_title() {
        return revision_title;
    }

    public void setRevision_title(String revision_title) {
        this.revision_title = revision_title;
    }

    public String getRevision_course_code() {
        return revision_course_code;
    }

    public void setRevision_course_code(String revision_course_code) {
        this.revision_course_code = revision_course_code;
    }

    public String getRevision_course_name() {
        return revision_course_name;
    }

    public void setRevision_course_name(String revision_course_name) {
        this.revision_course_name = revision_course_name;
    }

    public String getRevision_date() {
        return revision_date;
    }

    public void setRevision_date(String revision_date) {
        this.revision_date = revision_date;
    }

    public String getRevision_uploaded_by() {
        return revision_uploaded_by;
    }

    public void setRevision_uploaded_by(String revision_uploaded_by) {
        this.revision_uploaded_by = revision_uploaded_by;
    }

    public String getRevision_uploaded_on() {
        return revision_uploaded_on;
    }

    public void setRevision_uploaded_on(String revision_uploaded_on) {
        this.revision_uploaded_on = revision_uploaded_on;
    }
}
