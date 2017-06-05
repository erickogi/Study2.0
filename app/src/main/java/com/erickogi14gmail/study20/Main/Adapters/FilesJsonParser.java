package com.erickogi14gmail.study20.Main.Adapters;

import com.erickogi14gmail.study20.Main.models.Files_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/31/2017.
 */

public class FilesJsonParser {
    static ArrayList<Files_model> content_list;

    public static ArrayList<Files_model> parseData(String content) {

        JSONArray content_arry = null;
        Files_model model = null;
        try {


            content_list = new ArrayList<>();
            JSONObject jObj = new JSONObject(content);
            content_arry = jObj.getJSONArray("data");
            if (content_arry.length() < 1) {

            }

            for (int i = 0; i < content_arry.length(); i++) {

                JSONObject obj = content_arry.getJSONObject(i);
                model = new Files_model();

                model.setId(obj.getInt("id"));

                model.setPost_title(obj.getString("post_title"));

                model.setPost_date(obj.getString("post_date"));
                model.setPost_author(obj.getString("post_author"));
                model.setPost_pdf(obj.getString("post_pdf"));
                model.setPost_content(obj.getString("post_content"));


                content_list.add(model);
            }

            return content_list;

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
