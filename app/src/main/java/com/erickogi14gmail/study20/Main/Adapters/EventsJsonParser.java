package com.erickogi14gmail.study20.Main.Adapters;

import com.erickogi14gmail.study20.Main.models.Events_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/19/2017.
 */

public class EventsJsonParser {
    static ArrayList<Events_model> content_list;

    public static ArrayList<Events_model> parseData(String content) {

        JSONArray content_arry = null;
        Events_model model = null;
        try {


            content_list = new ArrayList<>();
            JSONObject jObj = new JSONObject(content);
            content_arry = jObj.getJSONArray("data");
            if (content_arry.length() < 1) {

            }

            for (int i = 0; i < content_arry.length(); i++) {

                JSONObject obj = content_arry.getJSONObject(i);
                model = new Events_model();

                model.setId(obj.getInt("id"));

                model.setEvent_title(obj.getString("event_tite"));

                model.setEvent_description(obj.getString("event_description"));
                model.setEvent_price(obj.getString("event_price"));
                model.setEvent_start(obj.getString("event_start"));
                model.setEvent_end(obj.getString("event_end"));
                model.setEvent_general_location(obj.getString("event_general_location"));


                model.setEvent_specific_location(obj.getString("event_specific_location"));
                model.setEvent_image(obj.getString("event_image"));
                model.setEvent_by(obj.getString("event_by"));
                model.setEvent_published_on(obj.getString("event_published_on"));
                model.setEvent_published_by(obj.getString("event_published_by"));
                model.setEvent_lat(obj.getString("event_lat"));
                model.setEvent_long(obj.getString("event_long"));

//                    private int id;
//                    private String event_title;
//                    private String  event_price;
//                    private  String event_description;
//                    private String event_start;
//                    private String event_end;
//                    private String event_general_location;
//                    private String event_specific_location;
//                    private String event_image;
//                    private String event_by;
//                    private String event_published_on;
//                    private String event_published_by;
//                    private String event_lat;
//                    private String event_long;

                content_list.add(model);
            }

            return content_list;

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
