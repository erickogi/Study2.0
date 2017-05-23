package com.erickogi14gmail.study20.Main.DB;

/**
 * Created by kimani kogi on 4/19/2017.
 */

public class DBKeys {

//TABLE CONTENTS
    public static final String CONTENT_TABLE = "content_table";


    // Labels Table  Columns names

    public static final String KEY_ID = "id";
    public static final String KEY_COURSE_ID = "course_id";
    public static final String KEY_COURSE_CODE = "course_code";
    public static final String KEY_COURSE_TITLE = "course_title";
    public static final String KEY_CHAPTER_NO = "chapter_no";
    public static final String KEY_CHAPTER_TITLE = "chapter_title";

    public static final String KEY_CHAPTER_CONTENT = "chapter_content";
    public static final String KEY_UPDATED_ON = "updated_on";

//TABLE COURSES
    public static final String COURSES_TABLE = "course_table";

    // Labels Table  Columns names


    public static final String KEY_NO_OF_CHAPTERS = "no_of_chapters";
    public static final String KEY_UPLOADED_BY = "uploaded_by";

    //Assignments
    public static final String ASSIGNMENTS_TABLE = "assignment_table";
    public static final String ASSIGNMENT_CONTENT_TABLE = "assignment_content_table";


    public static final String KEY_ASSIGNMENT_ID = "assignment_id";
    public static final String KEY_ASSIGNMENT_NAME = "assignment_name";
    public static final String KEY_ASSIGNMENT_CODE = "assignment_course_code";
    public static final String KEY_ASSIGNMENT_DONE_BY = "assignment_done_by";
    public static final String KEY_ASSIGNMENT_PUBLISHED_BY = "assignment_published_by";
    public static final String KEY_ASSIGNMENT_PUBLISHED_ON = "assignment_published_on";
    public static final String KEY_ASSIGNMENT_DONE_ON = "assignment_date";
    public static final String KEY_ASSIGNMENT_COURSE_NAME = "assignment_course";
    public static final String KEY_ASSIGNMENT_TYPE = "assignment_type";
    public static final String KEY_ASSIGNMENT_CONTENT = "assignment_content";

    ///REVISION
    public static final String REVISION_TABLE = "revision_table";
    public static final String KEY_REVISION_ID = "id";
    public static final String KEY_REVISION_TITLE = "revision_title";
    public static final String KEY_REVISION_COURSE_CODE = "revision_course_code";
    public static final String KEY_REVISION_COURSE_NAME = "revision_course_name";
    public static final String KEY_REVISION_DATE = "revision_date";
    public static final String KEY_REVISON_UPLOADED_BY = "revision_uploaded_by";
    public static final String KEY_REVISION_UPLOADED_ON = "revision_uploaded_on";
    public static final String KEY_REVISION_CONTENT = "revision_content";




    public  String COURSE_ID;
    public  String COURSE_TITLE;
    public  int CHAPTER_NO;
    public  String CHAPTER_TITLE;

    public  String CHAPTER_CONTENT;
    public  String UPDATED_ON;


    public  int NO_OF_CHAPTERS;
    public  String UPLOADED_BY;

}
