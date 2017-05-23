package com.erickogi14gmail.study20.Main.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.erickogi14gmail.study20.Main.models.Assignment_content_model;
import com.erickogi14gmail.study20.Main.models.Chapters;
import com.erickogi14gmail.study20.Main.models.Content_model;
import com.erickogi14gmail.study20.Main.models.Course_model;
import com.erickogi14gmail.study20.Main.models.Revision_model;

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


    ///////Assignments


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





}


