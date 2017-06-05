package com.erickogi14gmail.study20.Main.Adapters;

import android.util.Log;

import com.erickogi14gmail.study20.Main.models.Notifications_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/28/2017.
 */

public class NotificationsJsonParser {
    static ArrayList<Notifications_model> content_list;

    public static ArrayList<Notifications_model> parseData(String content) {

        JSONArray content_arry = null;
        Notifications_model model = null;
        try {


            content_list = new ArrayList<>();
            JSONObject jObj = new JSONObject(content);
            content_arry = jObj.getJSONArray("data");
            if (content_arry.length() < 1) {

            }

            for (int i = 0; i < content_arry.length(); i++) {

                JSONObject obj = content_arry.getJSONObject(i);
                model = new Notifications_model();

                model.setNotifications_id(obj.getInt("notifications_id"));
                Log.d("notidedd", "" + obj.getInt("notifications_id"));

                model.setNotification_channel(obj.getString("notifications_name"));
                model.setNotifications_title(obj.getString("notifications_title"));
                model.setNotifications_description(obj.getString("notifications_description"));
                model.setNotification_date(obj.getString("notification_date"));
                model.setNotification_status(0);


                content_list.add(model);
            }

            return content_list;

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}





