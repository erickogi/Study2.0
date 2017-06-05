package com.erickogi14gmail.study20.Main.Adapters;

import android.util.Log;

import com.erickogi14gmail.study20.Main.models.TimeTable_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/30/2017.
 */

public class TimeTableJsonParser {
    static ArrayList<TimeTable_model> data_model;

    public static ArrayList<TimeTable_model> parseData(String content) {

        JSONArray revision_arry = null;
        TimeTable_model model = null;
        try {


            data_model = new ArrayList<>();
            JSONObject jObj = new JSONObject(content);
            revision_arry = jObj.getJSONArray("data");

            Log.d("asddd", "" + revision_arry);
            if (revision_arry.length() < 1) {

            }
            for (int i = 0; i < revision_arry.length(); i++) {

                JSONObject obj = revision_arry.getJSONObject(i);
                model = new TimeTable_model();
                Log.d("asd", "" + obj);
                model.setTimetable_id(obj.getInt("timetable_id"));

                model.setTimetable_title(obj.getString("timetable_title"));

                model.setTimetable_course_name(obj.getString("timetable_course_name"));

                model.setTimetable_course_year(obj.getString("timetable_course_year"));

                model.setTimetable_published_by(obj.getString("timetable_published_by"));

                model.setTimetable_published_on(obj.getString("timetable_published_on"));

                model.setTimetable_content(obj.getString("timetable_content"));


                data_model.add(model);
            }
            return data_model;

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
