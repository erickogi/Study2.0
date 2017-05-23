package com.erickogi14gmail.study20.Main.Adapters;

import android.util.Log;

import com.erickogi14gmail.study20.Main.models.Revision_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/22/2017.
 */

public class RevisionJsonParser {
    static ArrayList<Revision_model> revision_model;

    public static ArrayList<Revision_model> parseData(String content) {

        JSONArray revision_arry = null;
        Revision_model model = null;
        try {


            revision_model = new ArrayList<>();
            JSONObject jObj = new JSONObject(content);
            revision_arry = jObj.getJSONArray("data");

            Log.d("asddd", "" + revision_arry);
            if (revision_arry.length() < 1) {

            }
            for (int i = 0; i < revision_arry.length(); i++) {

                JSONObject obj = revision_arry.getJSONObject(i);
                model = new Revision_model();
                Log.d("asd", "" + obj);
                model.setId(obj.getInt("id"));

                model.setRevision_title(obj.getString("revision_title"));

                model.setRevision_course_code(obj.getString("revision_course_code"));

                model.setRevision_course_name(obj.getString("revision_course_title"));

                model.setRevision_date(obj.getString("revision_date"));

                model.setRevision_uploaded_by(obj.getString("revision_uploaded_by"));

                model.setRevision_content(obj.getString("revision_content"));

                model.setRevision_uploaded_on(obj.getString("revision_uploaded_on"));


                revision_model.add(model);
            }
            return revision_model;

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
