package com.erickogi14gmail.study20.Main.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kimani kogi on 4/19/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

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




//
//    values.put(KEY_ASSIGNMENT_ID,data.getASSIGNMENT_ID());
//    values.put(KEY_ASSIGNMENT_NAME,data.getASSIGNMENT_NAME());
//
//
//    values.put(KEY_ASSIGNMENT_CODE, data.getASSIGNMENT_CODE());
//    values.put(KEY_ASSIGNMENT_DONE_BY, data.getASSIGNMENT_DONE_BY());
//
//    values.put(KEY_ASSIGNMENT_PUBLISHED_BY, data.getASSIGNMENT_PUBLISHED_BY());
//
//
//    values.put(KEY_ASSIGNMENT_PUBLISHED_ON, data.getASSIGNMENT_PUBLISHED_ON());
//
//    values.put(KEY_ASSIGNMENT_DONE_ON, data.getASSIGNMENT_DONE_ON());
//    values.put(KEY_COURSE_TITLE, data.getASSIGNMENT_COURSE_NAME());
//
//    values.put(KEY_ASSIGNMENT_TYPE, data.getASSIGNMENT_TYPE());
//
//
//    values.put(KEY_ASSIGNMENT_CONTENT, data.getASSIGNMENT_CONTENT());

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBKeys.COURSES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBKeys.CONTENT_TABLE);

        db.execSQL("DROP TABLE IF EXISTS " + DBKeys.ASSIGNMENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBKeys.ASSIGNMENT_CONTENT_TABLE);

        db.execSQL("DROP TABLE IF EXISTS " + DBKeys.REVISION_TABLE);


        // Create tables again
        onCreate(db);

    }

}


