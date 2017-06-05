package com.erickogi14gmail.study20.Main.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.erickogi14gmail.study20.Main.models.Assignment_content_model;
import com.erickogi14gmail.study20.Main.models.Channel_model;
import com.erickogi14gmail.study20.Main.models.Chapters;
import com.erickogi14gmail.study20.Main.models.Content_model;
import com.erickogi14gmail.study20.Main.models.Course_model;
import com.erickogi14gmail.study20.Main.models.Notifications_model;
import com.erickogi14gmail.study20.Main.models.Revision_model;
import com.erickogi14gmail.study20.Main.models.TimeTable_model;

import java.util.ArrayList;

import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_ASSIGNMENT_CODE;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_ASSIGNMENT_CONTENT;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_ASSIGNMENT_COURSE_NAME;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_ASSIGNMENT_DONE_BY;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_ASSIGNMENT_DONE_ON;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_ASSIGNMENT_ID;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_ASSIGNMENT_NAME;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_ASSIGNMENT_PUBLISHED_BY;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_ASSIGNMENT_PUBLISHED_ON;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_ASSIGNMENT_TYPE;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_COURSE_ID;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_COURSE_TITLE;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_NO_OF_CHAPTERS;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_REVISION_CONTENT;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_REVISION_COURSE_CODE;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_REVISION_COURSE_NAME;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_REVISION_DATE;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_REVISION_ID;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_REVISION_TITLE;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_REVISION_UPLOADED_ON;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_REVISON_UPLOADED_BY;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.KEY_UPLOADED_BY;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.NOTIFICATION_CHANNELS_ID;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.NOTIFICATION_CHANNEL_FROM;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.NOTIFICATION_DATE_;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.NOTIFICATION_DESCRIPTION_;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.NOTIFICATION_ID_;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.NOTIFICATION_READ_STATUS_;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.NOTIFICATION_TITLE_;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.TIMETABLE_CONTENT_;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.TIMETABLE_COURSE_;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.TIMETABLE_ID_;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.TIMETABLE_PUBLISHED_BY;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.TIMETABLE_PUBLISHED_ON_;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.TIMETABLE_TITLE;
import static com.erickogi14gmail.study20.Main.DB.DBKeys.TIMEYABLE_COURSE_YEAR;

/**
 * Created by kimani kogi on 4/19/2017.
 */

public class DBOperations {

    private DBHandler dbHandler;

    public DBOperations(Context context) {
        dbHandler = new DBHandler(context);
    }

    public boolean in(ArrayList<Content_model> data) {
        boolean success = false;
        try {

            SQLiteDatabase db = dbHandler.getWritableDatabase();

            //d
            for (int a = 0; a < data.size(); a++) {

                final SQLiteStatement insert = db.compileStatement("INSERT INTO content_table VALUES (?,?,?,?,?,?,?)");
                insert.bindString(1, String.valueOf(data.get(a).getId()));
                insert.bindString(2, data.get(a).getCOURSE_ID());
                insert.bindString(3, data.get(a).getCOURSE_TITLE().toUpperCase());
                insert.bindString(4, String.valueOf(data.get(a).getCHAPTER_NO()));
                insert.bindString(5, data.get(a).getCHAPTER_TITLE());
                insert.bindString(6, data.get(a).getCHAPTER_CONTENT());
                insert.bindString(7, data.get(a).getUPDATED_ON());
//edit: hehe forgot about most important thing

                if (insert.executeInsert() >= 1) {
                    success = true;

                } else {

                }
            }
            db.close();
        } catch (Exception l) {
            l.printStackTrace();

        }
        return success;
    }


    public boolean deleteCourse(String rowId) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        return db.delete(DBKeys.COURSES_TABLE, KEY_COURSE_ID + "= '" + rowId + "' ", null) > 0;
    }

    public boolean deleteContent(String rowId) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        return db.delete(DBKeys.CONTENT_TABLE, KEY_COURSE_ID + "= '" + rowId + "' ", null) > 0;
    }


    public ArrayList<Course_model> getCourseList() {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();


        ArrayList<Course_model> data = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DBKeys.COURSES_TABLE;


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            while (cursor.moveToNext()) {
                Course_model pojo = new Course_model();


                pojo.setCOURSE_ID(cursor.getString(1));
                pojo.setCOURSE_TITLE(cursor.getString(2));
                pojo.setNO_OF_CHAPTERS(cursor.getInt(3));

                pojo.setUPLOADED_BY(cursor.getString(4));


                data.add(pojo);

            }
        }
        db.close();
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return data;


    }

    public String getContent() {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String content = null;
        String QUERY = "SELECT * FROM " + DBKeys.CONTENT_TABLE + " ";
        Cursor cursor = db.rawQuery(QUERY, null);


        if (!cursor.isLast()) {

            while (cursor.moveToNext()) {


                content = cursor.getString(5);


            }
        }
        db.close();
        return content;
    }

    public ArrayList<Chapters> getChaptersByCourse(String courseId) {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();


        ArrayList<Chapters> data = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DBKeys.CONTENT_TABLE + " WHERE " + DBKeys.KEY_COURSE_ID + " = '" + courseId + "'";


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            while (cursor.moveToNext()) {
                Chapters pojo = new Chapters();


                pojo.setChapter_no(cursor.getInt(3));
                pojo.setChapter_title(cursor.getString(4));


                data.add(pojo);

            }
        }
        db.close();
        // looping through all rows and adding to list

//        if (cursor == null) {
//            return null;
//        } else if (!cursor.moveToFirst()) {
//            cursor.close();
//            return null;
//        }
        return data;


    }


    public String[] getChapterContentByChapterByCourse(String courseId, String chapterNo) {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String html[] = new String[2];

        ArrayList<Chapters> data = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DBKeys.CONTENT_TABLE + " WHERE " + DBKeys.KEY_COURSE_ID + " = '" + courseId + "' AND " + DBKeys.KEY_CHAPTER_NO + " ='" + chapterNo + "'";


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            if (cursor.moveToNext()) {
                Chapters pojo = new Chapters();

                html[0] = cursor.getString(5);
                html[1] = cursor.getString(2);


//                pojo.setChapter_title(cursor.getString(5));
//
//
//
//                data.add(pojo);

            }
        }
        db.close();
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return html;


    }


    public String getChapterContentByChapterByTitle(String courseId, String chapterTitle) {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String html = null;

        ArrayList<Chapters> data = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DBKeys.CONTENT_TABLE + " WHERE " + DBKeys.KEY_COURSE_ID + " = '" + courseId + "' AND " + DBKeys.KEY_CHAPTER_TITLE + " ='" + chapterTitle + "'";


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            if (cursor.moveToNext()) {


                html = cursor.getString(5);


            }
        }
        db.close();
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return html;


    }


    public boolean inCourse(Course_model data) {
        boolean success = false;

        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_COURSE_ID, data.getCOURSE_ID());
        values.put(KEY_COURSE_TITLE, data.getCOURSE_TITLE());

        values.put(KEY_NO_OF_CHAPTERS, data.getNO_OF_CHAPTERS());


        values.put(KEY_UPLOADED_BY, data.getUPLOADED_BY());


        // Inserting Row
        if (db.insert(DBKeys.COURSES_TABLE, null, values) >= 1) {
            success = true;
        }
        db.close();


        return success;


    }

    public boolean getCourseById(String courseId) {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        boolean isThere = false;
        ArrayList<Course_model> data = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DBKeys.COURSES_TABLE + "  WHERE " + DBKeys.KEY_COURSE_ID + " = '" + courseId + "' ";


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            if (cursor.moveToNext()) {
                isThere = true;
            }
        }
        db.close();
        // looping through all rows and adding to list


        return isThere;


    }

    public int getNoOfCourses() {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, DBKeys.COURSES_TABLE);
//        int no=0;
//        String countQuery = "SELECT  * FROM " + DBKeys.COURSES_TABLE;
//        SQLiteDatabase db = dbHandler.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        int cnt = cursor.getCount();
//        cursor.close();
//        return cnt;
    }


    ///////Assignments
    public int getNoOfAssignments() {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, DBKeys.ASSIGNMENT_CONTENT_TABLE);
//    int no=0;
//    String countQuery = "SELECT  * FROM " + DBKeys.ASSIGNMENTS_TABLE;
//    SQLiteDatabase db = dbHandler.getReadableDatabase();
//    Cursor cursor = db.rawQuery(countQuery, null);
//    int cnt = cursor.getCount();
//    cursor.close();
//    return cnt;
    }

    public boolean inAssignment(Assignment_content_model data) {
        boolean success = false;

        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(KEY_ASSIGNMENT_ID, data.getASSIGNMENT_ID());
        values.put(KEY_ASSIGNMENT_NAME, data.getASSIGNMENT_NAME());


        values.put(KEY_ASSIGNMENT_CODE, data.getASSIGNMENT_CODE());
        values.put(KEY_ASSIGNMENT_DONE_BY, data.getASSIGNMENT_DONE_BY());

        values.put(KEY_ASSIGNMENT_PUBLISHED_BY, data.getASSIGNMENT_PUBLISHED_BY());


        values.put(KEY_ASSIGNMENT_PUBLISHED_ON, data.getASSIGNMENT_PUBLISHED_ON());

        values.put(KEY_ASSIGNMENT_DONE_ON, data.getASSIGNMENT_DONE_ON());
        values.put(KEY_ASSIGNMENT_COURSE_NAME, data.getASSIGNMENT_COURSE_NAME());

        values.put(KEY_ASSIGNMENT_TYPE, data.getASSIGNMENT_TYPE());


        values.put(KEY_ASSIGNMENT_CONTENT, data.getASSIGNMENT_CONTENT());


        // Inserting Row
        if (db.insert(DBKeys.ASSIGNMENT_CONTENT_TABLE, null, values) >= 1) {
            success = true;
        }
        db.close();


        return success;


    }

    public ArrayList<Assignment_content_model> getAssignmentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();


        ArrayList<Assignment_content_model> data = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DBKeys.ASSIGNMENT_CONTENT_TABLE;


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            while (cursor.moveToNext()) {
                Assignment_content_model pojo = new Assignment_content_model();


                pojo.setASSIGNMENT_ID(cursor.getInt(0));
                pojo.setASSIGNMENT_NAME(cursor.getString(1));
                pojo.setASSIGNMENT_CODE(cursor.getString(2));
                pojo.setASSIGNMENT_DONE_BY(cursor.getString(3));
                pojo.setASSIGNMENT_PUBLISHED_BY(cursor.getString(4));
                pojo.setASSIGNMENT_PUBLISHED_ON(cursor.getString(5));
                pojo.setASSIGNMENT_DONE_ON(cursor.getString(6));
                pojo.setASSIGNMENT_COURSE_NAME(cursor.getString(7));
                pojo.setASSIGNMENT_TYPE(cursor.getString(8));
                pojo.setASSIGNMENT_CONTENT(cursor.getString(9));


                data.add(pojo);

            }
        }
        db.close();
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return data;


    }

    public ArrayList<Assignment_content_model> getAssignmentListById(String assId) {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();


        ArrayList<Assignment_content_model> data = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DBKeys.ASSIGNMENT_CONTENT_TABLE + "  WHERE " + DBKeys.KEY_ASSIGNMENT_ID + " = '" + assId + "' ";


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            while (cursor.moveToNext()) {
                Assignment_content_model pojo = new Assignment_content_model();


                pojo.setASSIGNMENT_ID(cursor.getInt(0));
                pojo.setASSIGNMENT_NAME(cursor.getString(1));
                pojo.setASSIGNMENT_CODE(cursor.getString(2));
                pojo.setASSIGNMENT_DONE_BY(cursor.getString(3));
                pojo.setASSIGNMENT_PUBLISHED_BY(cursor.getString(4));
                pojo.setASSIGNMENT_PUBLISHED_ON(cursor.getString(5));
                pojo.setASSIGNMENT_DONE_ON(cursor.getString(6));
                pojo.setASSIGNMENT_COURSE_NAME(cursor.getString(7));
                pojo.setASSIGNMENT_TYPE(cursor.getString(8));
                pojo.setASSIGNMENT_CONTENT(cursor.getString(9));


                data.add(pojo);

            }
        }
        db.close();
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return data;


    }

    public boolean getAssignmentById(String assId) {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        boolean isThere = false;

        String QUERY = "SELECT * FROM " + DBKeys.ASSIGNMENT_CONTENT_TABLE + "  WHERE " + DBKeys.KEY_ASSIGNMENT_ID + " = '" + assId + "' ";


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            if(cursor.moveToNext()) {
                isThere = true;
            }
        }
        db.close();
        // looping through all rows and adding to list


        return isThere;


    }

    public boolean deleteAssignment(String rowId) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        return db.delete(DBKeys.ASSIGNMENT_CONTENT_TABLE, KEY_ASSIGNMENT_ID + "= '" + rowId + "' ", null) > 0;
    }


    ////////REVISION

    public int getNoOfRevesion() {
        int no = 0;
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, DBKeys.REVISION_TABLE);
//        String countQuery = "SELECT  * FROM " + DBKeys.REVISION_TABLE;
//        SQLiteDatabase db = dbHandler.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        int cnt = cursor.getCount();
//        cursor.close();
//        return cnt;
    }
    public boolean inRevision(Revision_model data) {
        boolean success = false;

        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(KEY_REVISION_ID, data.getId());
        values.put(KEY_REVISION_TITLE, data.getRevision_title());


        values.put(KEY_REVISION_COURSE_CODE, data.getRevision_course_code());
        values.put(KEY_REVISION_COURSE_NAME, data.getRevision_course_name());

        values.put(KEY_REVISION_DATE, data.getRevision_date());


        values.put(KEY_REVISON_UPLOADED_BY, data.getRevision_uploaded_by());

        values.put(KEY_REVISION_CONTENT, data.getRevision_content());


        values.put(KEY_REVISION_UPLOADED_ON, data.getRevision_uploaded_on());


        // Inserting Row
        if (db.insert(DBKeys.REVISION_TABLE, null, values) >= 1) {
            success = true;
        }
        db.close();


        return success;


    }

    public ArrayList<Revision_model> getRevisionList() {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();


        ArrayList<Revision_model> data = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DBKeys.REVISION_TABLE;


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            while (cursor.moveToNext()) {
                Revision_model pojo = new Revision_model();


                pojo.setId(cursor.getInt(0));
                pojo.setRevision_title(cursor.getString(1));
                pojo.setRevision_course_code(cursor.getString(2));
                pojo.setRevision_course_name(cursor.getString(3));
                pojo.setRevision_date(cursor.getString(4));
                pojo.setRevision_uploaded_by(cursor.getString(5));
                pojo.setRevision_content(cursor.getString(6));
                pojo.setRevision_uploaded_on(cursor.getString(7));


                data.add(pojo);

            }
        }
        db.close();
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return data;


    }

    public ArrayList<Revision_model> getRevisionListById(String assId) {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();


        ArrayList<Revision_model> data = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DBKeys.REVISION_TABLE + "  WHERE " + DBKeys.KEY_REVISION_ID + " = '" + assId + "' ";


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            while (cursor.moveToNext()) {
                Revision_model pojo = new Revision_model();


                pojo.setId(cursor.getInt(0));
                pojo.setRevision_title(cursor.getString(1));
                pojo.setRevision_course_code(cursor.getString(2));
                pojo.setRevision_course_name(cursor.getString(3));
                pojo.setRevision_date(cursor.getString(4));
                pojo.setRevision_uploaded_by(cursor.getString(5));
                pojo.setRevision_content(cursor.getString(6));
                pojo.setRevision_uploaded_on(cursor.getString(7));


                data.add(pojo);

            }
        }
        db.close();
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return data;


    }

    public boolean getRevisionById(String assId) {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        boolean isThere = false;

        String QUERY = "SELECT * FROM " + DBKeys.REVISION_TABLE + "  WHERE " + DBKeys.KEY_REVISION_ID + " = '" + assId + "' ";


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            if (cursor.moveToNext()) {
                isThere = true;
            }
        }
        db.close();
        // looping through all rows and adding to list


        return isThere;


    }

    public boolean deleteRevision(String rowId) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        return db.delete(DBKeys.REVISION_TABLE, KEY_REVISION_ID + "= '" + rowId + "' ", null) > 0;
    }

//////NOTIFICATION /CHANNELS


    public boolean inNotifications(Notifications_model data) {
        boolean success = false;


        SQLiteDatabase db = dbHandler.getWritableDatabase();
        //  SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        // for (int a = 0; a < data.size(); a++) {
        try {

            values.put(NOTIFICATION_ID_, data.getNotifications_id());
            values.put(NOTIFICATION_CHANNEL_FROM, data.getNotification_channel());


            values.put(NOTIFICATION_TITLE_, data.getNotifications_title());
            values.put(NOTIFICATION_DESCRIPTION_, data.getNotifications_description());

            values.put(NOTIFICATION_READ_STATUS_, data.getNotification_status());


            values.put(NOTIFICATION_DATE_, data.getNotification_date());

            if (db.insert(DBKeys.NOTIFICATION_TABLE, null, values) >= 1) {
                success = true;
            }
            db.close();


        } catch (Exception nm) {

        }

        //d
//            for (int a = 0; a < data.size(); a++) {
//try {
//    SQLiteStatement insert = db.compileStatement("INSERT INTO notifications_table VALUES (?,?,?,?,?,?)");
//    insert.bindString(1, String.valueOf(data.get(a).getNotifications_id()));
//    Log.d("notid",String.valueOf(data.get(a).getNotifications_id()));
//    insert.bindString(2, data.get(a).getNotification_channel());
//    insert.bindString(3, data.get(a).getNotifications_title());
//    insert.bindString(4, data.get(a).getNotifications_description());
//    insert.bindString(5, String.valueOf(data.get(a).getNotification_status()));
//    insert.bindString(6, data.get(a).getNotification_date());
//
//
//    if (insert.executeInsert() >= 1) {
//        success = true;
//
//    } else {
//
//    }
//}
//catch (Exception nm){
//
//}
//            }
        //        db.close();

        return success;
    }

    public boolean inNotificationChannels(Channel_model data) {
        boolean success = false;
        try {

            SQLiteDatabase db = dbHandler.getWritableDatabase();


            final SQLiteStatement insert = db.compileStatement("INSERT INTO channels_table VALUES (?,?,?,?)");
            insert.bindString(1, String.valueOf(data.getNotification_c_id()));
            insert.bindString(2, data.getNotification_c_name());
            insert.bindString(3, data.getNotification_c_desc());
            insert.bindString(4, data.getNotification_c_color());


            if (insert.executeInsert() >= 1) {
                success = true;

            } else {

            }

            db.close();
        } catch (Exception l) {
            l.printStackTrace();

        }
        return success;
    }

    public boolean deleteNotificationChannel(String rowId) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        return db.delete(DBKeys.NOTIFICATION_CHANNELS_TABLE, NOTIFICATION_CHANNELS_ID + "= '" + rowId + "' ", null) > 0;
    }

    public boolean deleteNotification(String rowId) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        return db.delete(DBKeys.NOTIFICATION_TABLE, NOTIFICATION_ID_ + "= '" + rowId + "' ", null) > 0;
    }


    public ArrayList<Channel_model> getChannelsList() {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();


        ArrayList<Channel_model> data = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DBKeys.NOTIFICATION_CHANNELS_TABLE;


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            while (cursor.moveToNext()) {
                Channel_model pojo = new Channel_model();


                pojo.setNotification_c_id(cursor.getInt(0));
                pojo.setNotification_c_name(cursor.getString(1));
                pojo.setNotification_c_desc(cursor.getString(2));

                pojo.setNotification_c_color(cursor.getString(3));


                data.add(pojo);

            }
        }
        db.close();
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return data;


    }

    public ArrayList<Notifications_model> getNotificationByChannel(String channel) {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();


        ArrayList<Notifications_model> data = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DBKeys.NOTIFICATION_TABLE + " WHERE " + DBKeys.NOTIFICATION_CHANNEL_FROM + " = '" + channel + "' ORDER BY " + DBKeys.NOTIFICATION_ID_ + " DESC ";


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            while (cursor.moveToNext()) {
                Notifications_model pojo = new Notifications_model();


                pojo.setNotifications_id(cursor.getInt(0));
                pojo.setNotification_channel(cursor.getString(1));
                pojo.setNotifications_title(cursor.getString(2));
                pojo.setNotifications_description(cursor.getString(3));
                pojo.setNotification_status(cursor.getInt(4));
                pojo.setNotification_date(cursor.getString(5));


                data.add(pojo);

            }
        }
        db.close();
        // looping through all rows and adding to list

//        if (cursor == null) {
//            return null;
//        } else if (!cursor.moveToFirst()) {
//            cursor.close();
//            return null;
//        }
        return data;


    }


    public ArrayList<Notifications_model> getNotificationsUnreadByChannel(int status, String channel) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        ArrayList<Notifications_model> data = new ArrayList<>();
        String countQuery = "SELECT * FROM " + DBKeys.NOTIFICATION_TABLE + " " +
                "WHERE " + DBKeys.NOTIFICATION_READ_STATUS_ + " = '" + status + "' AND " + DBKeys.NOTIFICATION_CHANNEL_FROM + "='" + channel + "' ";
//        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);


        if (!cursor.isLast()) {

            while (cursor.moveToNext()) {
                Notifications_model pojo = new Notifications_model();


                pojo.setNotifications_id(cursor.getInt(0));
                pojo.setNotification_channel(cursor.getString(1));
                pojo.setNotifications_title(cursor.getString(2));
                pojo.setNotifications_description(cursor.getString(3));
                pojo.setNotification_status(cursor.getInt(4));
                pojo.setNotification_date(cursor.getString(5));


                data.add(pojo);

            }
        }
        db.close();

        return data;


    }

    public int getNoOfNotificationsUnread(int status) {
        int total = 0;
        try {
            ArrayList<Channel_model> channels = getChannelsList();
            if (channels.isEmpty()) {

            } else {

                SQLiteDatabase db = dbHandler.getReadableDatabase();
                for (int a = 0; a < channels.size(); a++) {

                    total = total + (int) DatabaseUtils.longForQuery(db, "SELECT Count(*) " +
                            " FROM " + DBKeys.NOTIFICATION_TABLE + " " +
                            "WHERE " + DBKeys.NOTIFICATION_READ_STATUS_ + " = '" + status + "' " +
                            "AND " + DBKeys.NOTIFICATION_CHANNEL_FROM + "='" + channels.get(a).getNotification_c_name() + "'", null);
                }
            }

        } catch (Exception nm) {

        }
        return total;

    }

    public int getNoOfNotificationsUnreadByChannel(int status, String chnName) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        return (int) DatabaseUtils.longForQuery(db, "SELECT Count(*)  FROM " + DBKeys.NOTIFICATION_TABLE + " " +
                "WHERE " + DBKeys.NOTIFICATION_READ_STATUS_ + " = '" + status + "' AND " + DBKeys.NOTIFICATION_CHANNEL_FROM + " ='" + chnName + "'", null);

    }

    public boolean getChannelById(String Id) {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        boolean isThere = false;
        ArrayList<Course_model> data = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DBKeys.NOTIFICATION_CHANNELS_TABLE + "  WHERE " + DBKeys.NOTIFICATION_CHANNELS_ID + " = '" + Id + "' ";


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            if (cursor.moveToNext()) {
                isThere = true;
            }
        }
        db.close();
        // looping through all rows and adding to list


        return isThere;


    }

    public void updateToRead(int status, String chan) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBKeys.NOTIFICATION_READ_STATUS_, status);
        db.update(DBKeys.NOTIFICATION_TABLE, cv, DBKeys.NOTIFICATION_CHANNEL_FROM + "='" + chan + "'", null);
    }

    ////TIMETABLES
    public int getNoOfTimeTables() {
        int no = 0;
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, DBKeys.TIMETABLES_TABLE);

    }

    public boolean inTimeTable(TimeTable_model data) {
        boolean success = false;

        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(TIMETABLE_ID_, data.getTimetable_id());
        values.put(TIMETABLE_TITLE, data.getTimetable_title());


        values.put(TIMETABLE_COURSE_, data.getTimetable_course_name());
        values.put(TIMEYABLE_COURSE_YEAR, data.getTimetable_course_year());

        values.put(TIMETABLE_PUBLISHED_BY, data.getTimetable_published_by());


        values.put(TIMETABLE_PUBLISHED_ON_, data.getTimetable_published_on());

        values.put(TIMETABLE_CONTENT_, data.getTimetable_content());


        // Inserting Row
        if (db.insert(DBKeys.TIMETABLES_TABLE, null, values) >= 1) {
            success = true;
        }
        db.close();


        return success;


    }

    public ArrayList<TimeTable_model> getTimeTablesList() {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();


        ArrayList<TimeTable_model> data = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DBKeys.TIMETABLES_TABLE;


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            while (cursor.moveToNext()) {
                TimeTable_model pojo = new TimeTable_model();


                pojo.setTimetable_id(cursor.getInt(0));
                pojo.setTimetable_title(cursor.getString(1));
                pojo.setTimetable_course_name(cursor.getString(2));
                pojo.setTimetable_course_year(cursor.getString(3));
                pojo.setTimetable_published_by(cursor.getString(4));
                pojo.setTimetable_published_on(cursor.getString(5));
                pojo.setTimetable_content(cursor.getString(6));


                data.add(pojo);

            }
        }
        db.close();
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return data;


    }

    public ArrayList<TimeTable_model> getTimeTablesListtById(String assId) {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();


        ArrayList<TimeTable_model> data = new ArrayList<>();
        String QUERY = "SELECT * FROM " + DBKeys.TIMETABLES_TABLE + "  WHERE " + DBKeys.TIMETABLE_ID_ + " = '" + assId + "' ";


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            while (cursor.moveToNext()) {
                TimeTable_model pojo = new TimeTable_model();


                pojo.setTimetable_id(cursor.getInt(0));
                pojo.setTimetable_title(cursor.getString(1));
                pojo.setTimetable_course_name(cursor.getString(2));
                pojo.setTimetable_course_year(cursor.getString(3));
                pojo.setTimetable_published_by(cursor.getString(4));
                pojo.setTimetable_published_on(cursor.getString(5));
                pojo.setTimetable_content(cursor.getString(6));


                data.add(pojo);

            }
        }
        db.close();
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return data;


    }

    public boolean getTimeTableById(String assId) {
        //Open connection to read only
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        boolean isThere = false;

        String QUERY = "SELECT * FROM " + DBKeys.TIMETABLES_TABLE + "  WHERE " + DBKeys.TIMETABLE_ID_ + " = '" + assId + "' ";


        Cursor cursor = db.rawQuery(QUERY, null);

        if (!cursor.isLast()) {

            if (cursor.moveToNext()) {
                isThere = true;
            }
        }
        db.close();
        // looping through all rows and adding to list


        return isThere;


    }

    public boolean deleteTimeTable(String rowId) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        return db.delete(DBKeys.TIMETABLES_TABLE, TIMETABLE_ID_ + "= '" + rowId + "' ", null) > 0;
    }
}


