package com.erickogi14gmail.study20.Main.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kimani kogi on 4/19/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    //CHANNELS
    public static final String NOTIFICATION_CHANNELS_TABLE = "channels_table";
    public static final String NOTIFICATION_CHANNELS_ID = "notification_c_id";
    public static final String NOTIFICATION_CHANNELS_NAME = "notification_c_name";
    public static final String NOTIFICATION_CHANNEL_DESC_ = "notification_c_desc";
    public static final String NOTIFICATION_CHANNELS_COLOR = "notification_c_color";
    //NOTICES
    public static final String NOTIFICATION_TABLE = "notifications_table";
    public static final String NOTIFICATION_ID_ = "notifications_id";
    public static final String NOTIFICATION_TITLE_ = "notifications_title";
    public static final String NOTIFICATION_DESCRIPTION_ = "notifications_description";
    public static final String NOTIFICATION_DATE_ = "notification_date";
    public static final String NOTIFICATION_READ_STATUS_ = "notification_status";
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "study.db";
    String CREATE_TABLE_CONTENT = "CREATE TABLE " + DBKeys.CONTENT_TABLE + "("
            + DBKeys.KEY_ID + " INTEGER PRIMARY KEY ,"

            + DBKeys.KEY_COURSE_ID + " VARCHAR, "
            + DBKeys.KEY_COURSE_TITLE+ " TEXT, "

            + DBKeys.KEY_CHAPTER_NO+ " INTEGER ,"
            + DBKeys.KEY_CHAPTER_TITLE + " TEXT ,"

            + DBKeys.KEY_CHAPTER_CONTENT + " Text ,"


            + DBKeys.KEY_UPDATED_ON + " TEXT"

            + ")";
    String CREATE_TABLE_COURSES = "CREATE TABLE " + DBKeys.COURSES_TABLE+ "("
            + DBKeys.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"

            + DBKeys.KEY_COURSE_ID + " TEXT, "
            + DBKeys.KEY_COURSE_TITLE+ " TEXT, "

            + DBKeys.KEY_NO_OF_CHAPTERS+ " INTEGER ,"

            + DBKeys.KEY_UPLOADED_BY + " TEXT"

            + ")";
    String CREATE_TABLE_ASSIGNMENTS = "CREATE TABLE " + DBKeys.ASSIGNMENTS_TABLE + "("
            + DBKeys.KEY_ASSIGNMENT_ID + " INTEGER PRIMARY KEY  ,"


            + DBKeys.KEY_ASSIGNMENT_NAME + " TEXT, "

            + DBKeys.KEY_ASSIGNMENT_CODE + " VARCHAR ,"

            + DBKeys.KEY_ASSIGNMENT_DONE_BY + " VARCHAR ,"

            + DBKeys.KEY_ASSIGNMENT_TYPE + " VARCHAR ,"

            + DBKeys.KEY_ASSIGNMENT_COURSE_NAME + " VARCHAR"

            + ")";
    String CREATE_TABLE_ASSIGNMENTS_CONTENT = "CREATE TABLE " + DBKeys.ASSIGNMENT_CONTENT_TABLE + "("
            + DBKeys.KEY_ASSIGNMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"

            + DBKeys.KEY_ASSIGNMENT_NAME + " TEXT, "

            + DBKeys.KEY_ASSIGNMENT_CODE + " TEXT, "

            + DBKeys.KEY_ASSIGNMENT_DONE_BY + " VARCHAR, "

            + DBKeys.KEY_ASSIGNMENT_PUBLISHED_BY + " VARCHAR, "

            + DBKeys.KEY_ASSIGNMENT_PUBLISHED_ON + " VARCHAR ,"

            + DBKeys.KEY_ASSIGNMENT_DONE_ON + " VARCHAR ,"

            + DBKeys.KEY_ASSIGNMENT_COURSE_NAME + " VARCHAR ,"

            + DBKeys.KEY_ASSIGNMENT_TYPE + " VARCHAR ,"

            + DBKeys.KEY_ASSIGNMENT_CONTENT + " TEXT "


            + ")";
    String CREATE_TABLE_REVISON = "CREATE TABLE " + DBKeys.REVISION_TABLE + "("
            + DBKeys.KEY_REVISION_ID + " INTEGER PRIMARY KEY  ,"


            + DBKeys.KEY_REVISION_TITLE + " TEXT, "

            + DBKeys.KEY_REVISION_COURSE_CODE + " VARCHAR ,"

            + DBKeys.KEY_REVISION_COURSE_NAME + " VARCHAR ,"

            + DBKeys.KEY_REVISION_DATE + " VARCHAR ,"

            + DBKeys.KEY_REVISON_UPLOADED_BY + " VARCHAR ,"

            + DBKeys.KEY_REVISION_CONTENT + " VARCHAR ,"

            + DBKeys.KEY_REVISION_UPLOADED_ON + " VARCHAR"

            + ")";
    String CREATE_TABLE_NOTIFICATION_CHANNELS = "CREATE TABLE " + DBKeys.NOTIFICATION_CHANNELS_TABLE + "("
            + DBKeys.NOTIFICATION_CHANNELS_ID + " INTEGER PRIMARY KEY  ,"


            + DBKeys.NOTIFICATION_CHANNELS_NAME + " VARCHAR, "

            + DBKeys.NOTIFICATION_CHANNEL_DESC_ + " VARCHAR ,"


            + DBKeys.NOTIFICATION_CHANNELS_COLOR + " VARCHAR"

            + ")";

    String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE " + DBKeys.NOTIFICATION_TABLE + "("
            + DBKeys.NOTIFICATION_ID_ + " INTEGER PRIMARY KEY  ,"

            + DBKeys.NOTIFICATION_CHANNEL_FROM + " VARCHAR ,"


            + DBKeys.NOTIFICATION_TITLE_ + " VARCHAR, "

            + DBKeys.NOTIFICATION_DESCRIPTION_ + " VARCHAR ,"


            + DBKeys.NOTIFICATION_READ_STATUS_ + " VARCHAR ,"


            + DBKeys.NOTIFICATION_DATE_ + " VARCHAR"

            + ")";

    String CREATE_TABLE_TIMETABLES = "CREATE TABLE " + DBKeys.TIMETABLES_TABLE + "("
            + DBKeys.TIMETABLE_ID_ + " INTEGER PRIMARY KEY  ,"

            + DBKeys.TIMETABLE_TITLE + " VARCHAR ,"


            + DBKeys.TIMETABLE_COURSE_ + " VARCHAR, "

            + DBKeys.TIMEYABLE_COURSE_YEAR + " VARCHAR ,"


            + DBKeys.TIMETABLE_PUBLISHED_BY + " VARCHAR ,"

            + DBKeys.TIMETABLE_PUBLISHED_ON_ + " VARCHAR ,"


            + DBKeys.TIMETABLE_CONTENT_ + " VARCHAR"

            + ")";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_COURSES);
        db.execSQL(CREATE_TABLE_CONTENT);
        db.execSQL(CREATE_TABLE_ASSIGNMENTS);
        db.execSQL(CREATE_TABLE_ASSIGNMENTS_CONTENT);
        db.execSQL(CREATE_TABLE_REVISON);
        db.execSQL(CREATE_TABLE_NOTIFICATION_CHANNELS);
        db.execSQL(CREATE_TABLE_NOTIFICATIONS);
        db.execSQL(CREATE_TABLE_TIMETABLES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBKeys.COURSES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBKeys.CONTENT_TABLE);

        db.execSQL("DROP TABLE IF EXISTS " + DBKeys.ASSIGNMENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBKeys.ASSIGNMENT_CONTENT_TABLE);

        db.execSQL("DROP TABLE IF EXISTS " + DBKeys.REVISION_TABLE);

        db.execSQL("DROP TABLE IF EXISTS " + DBKeys.NOTIFICATION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBKeys.NOTIFICATION_CHANNELS_TABLE);

        db.execSQL("DROP TABLE IF EXISTS " + DBKeys.TIMETABLES_TABLE);


        //  db.execSQL(CREATE_TABLE_NOTIFICATION_CHANNELS);
        // db.execSQL(CREATE_TABLE_NOTIFICATIONS);


        // Create tables again
        onCreate(db);

    }

}


