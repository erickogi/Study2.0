package com.erickogi14gmail.study20.Main.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by kimani kogi on 5/27/2017.
 */

public class VolleyService {

    IResult mResultCallback = null;
    Context mContext;

    public VolleyService(IResult resultCallback, Context context) {
        mResultCallback = resultCallback;
        mContext = context;
    }


//    public void postDataVolley(final String requestType, String url,JSONObject sendObj){
//        try {
//            RequestQueue queue = Volley.newRequestQueue(mContext);
//
//            JsonObjectRequest jsonObj = new JsonObjectRequest(url,sendObj, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    if(mResultCallback != null)
//                        mResultCallback.notifySuccess(requestType,response);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    if(mResultCallback != null)
//                        mResultCallback.notifyError(requestType,error);
//                }
//            });
//
//            queue.add(jsonObj);
//
//        }catch(Exception e){
//
//        }
//    }

    public void getDataVolley(final String requestType, String url) {


        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

//            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
                            if (mResultCallback != null)
                                mResultCallback.notifySuccess(requestType, response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (mResultCallback != null)
                        mResultCallback.notifyError(requestType, error);
                }
            });

            queue.add(stringRequest);

        } catch (Exception e) {

        }
    }

    public void getDataVolley2(final String requestType, String url) {


        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

//            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
                            if (mResultCallback != null)
                                mResultCallback.notifySuccess(requestType, response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (mResultCallback != null)
                        mResultCallback.notifyError(requestType, error);
                }
            });

            queue.add(stringRequest);

        } catch (Exception e) {

        }
    }
}


