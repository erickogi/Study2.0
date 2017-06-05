package com.erickogi14gmail.study20.Main.Adapters;

import com.erickogi14gmail.study20.Main.models.Channel_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/28/2017.
 */

public class ChannelsJsonParser {
    static ArrayList<Channel_model> content_list;

    public static ArrayList<Channel_model> parseData(String content) {

        JSONArray content_arry = null;
        Channel_model model = null;
        try {


            content_list = new ArrayList<>();
            JSONObject jObj = new JSONObject(content);
            content_arry = jObj.getJSONArray("data");
            if (content_arry.length() < 1) {

            }

            for (int i = 0; i < content_arry.length(); i++) {

                JSONObject obj = content_arry.getJSONObject(i);
                model = new Channel_model();

                model.setNotification_c_id(obj.getInt("notification_c_id"));

                model.setNotification_c_name(obj.getString("notification_c_name"));
                model.setNotification_c_desc(obj.getString("notification_c_desc"));
                model.setNotification_c_color(obj.getString("notification_c_color"));


                content_list.add(model);
            }

            return content_list;

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}


