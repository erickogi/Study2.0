package com.erickogi14gmail.study20.Main.TimeTables;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.erickogi14gmail.study20.Main.Adapters.TimaTablesAdapter;
import com.erickogi14gmail.study20.Main.Adapters.TimeTableJsonParser;
import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.Main.DB.DBOperations;
import com.erickogi14gmail.study20.Main.models.TimeTable_model;
import com.erickogi14gmail.study20.Main.utills.RecyclerTouchListener;
import com.erickogi14gmail.study20.Main.volley.IResult;
import com.erickogi14gmail.study20.Main.volley.VolleyService;
import com.erickogi14gmail.study20.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/30/2017.
 */

public class TimeTable_add extends Fragment {


    static View view;

    static RecyclerView.LayoutManager mLayoutManager;
    static ArrayList<TimeTable_model> titmeTable_model;
    static ArrayList<TimeTable_model> temp_model;

    static TimaTablesAdapter adapter;
    static IResult mResultCallback = null;
    static VolleyService mVolleyService;
    DBOperations dbOperations;
    SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recyclerView_vertical;
    ProgressDialog progressDialog;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private int positionClicked = 0;

//    void insertCourse(TimeTable_model data) {
//        dbOperations = new DBOperations(getContext());
//
//        if (dbOperations.inCourse(data)) {
//
//            progressDialog.dismiss();
//
//            StyleableToast st = new StyleableToast(getContext(), "Saved Successfully", Toast.LENGTH_SHORT);
//            st.setBackgroundColor(Color.parseColor("#ff9040"));
//            st.setTextColor(Color.WHITE);
//
//
//            st.setMaxAlpha();
//            st.show();
//
//        } else {
//            StyleableToast st = new StyleableToast(getContext(), "Storage error 2", Toast.LENGTH_SHORT);
//            st.setBackgroundColor(Color.parseColor("#ff9040"));
//            st.setTextColor(Color.WHITE);
//            st.setIcon(R.drawable.ic_error_outline_white_24dp);
//
//            st.setMaxAlpha();
//            st.show();
//            progressDialog.dismiss();
//
//        }
//
//    }

    static void getRecyclerView_sources(Context context) {
        requestDataSources(api.TIMETABLES_END_POINT, context);
    }

    static void filter(String text) {
        try {
            ArrayList<TimeTable_model> temp = new ArrayList();
            for (TimeTable_model d : titmeTable_model) {

                if (d.getTimetable_course_name().toLowerCase().contains(text.toLowerCase())
                        || d.getTimetable_title().toLowerCase().contains(text.toLowerCase())) {
                    temp.add(d);
                }

            }
            temp_model = temp;
            adapter.updateList(temp);
        } catch (Exception nm) {
            nm.printStackTrace();
        }

    }

    public static void requestDataSources(String uri, Context context) {


        mVolleyService = new VolleyService(mResultCallback, context);
        mVolleyService.getDataVolley("GETCALL_TIMETABLES_SOURCES", uri);


    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */

        view = inflater.inflate(R.layout.fragment_courses, container, false);
        progressDialog = new ProgressDialog(view.getContext());
        initVolleyCallback();


        recyclerView_vertical = (RecyclerView) view.findViewById(R.id.recycle_view);
        swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipe_refresh_layout.setBackgroundResource(android.R.color.white);
        swipe_refresh_layout.setColorSchemeResources(android.R.color.white, android.R.color.holo_purple, android.R.color.white);

        swipe_refresh_layout.setRefreshing(true);

        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout.setRefreshing(true);
                getRecyclerView_sources(getActivity());

            }
        });
        getRecyclerView_sources(getActivity());


        recyclerView_vertical.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView_vertical, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {


                String code = String.valueOf(temp_model.get(position).getTimetable_id());
                dbOperations = new DBOperations(getContext());
                Log.d("kl", code);
                if (dbOperations.getTimeTableById(code)) {
                    Snackbar.make(view, "You have this Revision Item Downloaded Already", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {


                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Downloading content.....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                    progressDialog.setIndeterminate(true);

                    progressDialog.show();


                    dbOperations = new DBOperations(getContext());
                    code = code.replaceAll(" ", "%20");
                    requestDataContent(api.TIMETABLES_END_POINT + code, position);


                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    void insert(ArrayList<TimeTable_model> content_models, int position) {

        dbOperations = new DBOperations(getContext());

        if (dbOperations.inTimeTable(content_models.get(0))) {

            content_models.clear();
            getRecyclerView_sources(getActivity());
            progressDialog.dismiss();
            TimeTable_saved.setRecyclerView(getActivity());

            StyleableToast st = new StyleableToast(getContext(), "Saved Successfully", Toast.LENGTH_SHORT);
            st.setBackgroundColor(Color.parseColor("#ff9040"));
            st.setTextColor(Color.WHITE);


            st.setMaxAlpha();
            st.show();


        } else {
            StyleableToast st = new StyleableToast(getContext(), "Storage error 1", Toast.LENGTH_SHORT);
            st.setBackgroundColor(Color.parseColor("#ff9040"));
            st.setTextColor(Color.WHITE);
            st.setIcon(R.drawable.ic_error_outline_white_24dp);

            st.setMaxAlpha();
            st.show();
            progressDialog.dismiss();

        }
    }

    public void setRecyclerView_courses(ArrayList<TimeTable_model> titmeTable_modelArrayList) {
        try {
            titmeTable_model.clear();
        } catch (Exception MN) {
            MN.printStackTrace();
        }
        titmeTable_model = titmeTable_modelArrayList;
        temp_model = titmeTable_model;
//        Log.d("kj",""+assignment_model.get(1).getASSIGNMENT_COURSE_NAME());

        adapter = new TimaTablesAdapter(getContext(), titmeTable_modelArrayList, 0);
        adapter.notifyDataSetChanged();

        recyclerView_vertical = (RecyclerView) view.findViewById(R.id.recycle_view);

        mLayoutManager = new LinearLayoutManager(getContext());


        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);


        recyclerView_vertical.setLayoutManager(mStaggeredLayoutManager);

        recyclerView_vertical.setItemAnimator(new DefaultItemAnimator());


        recyclerView_vertical.setAdapter(adapter);
        swipe_refresh_layout.setRefreshing(false);
    }

    void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                ArrayList<TimeTable_model> titmeTable_modelArrayList = new ArrayList<>();
                ArrayList<TimeTable_model> titmeTable_modelArrayListContent;
                if (requestType.equals("GETCALL_TIMETABLES_SOURCES")) {
                    titmeTable_modelArrayList = TimeTableJsonParser.parseData(response);
                    Log.d("asdd", "" + titmeTable_modelArrayList);
                    setRecyclerView_courses(titmeTable_modelArrayList);
                } else if (requestType.equals("GETCALL_TIMETABLES_CONTENT")) {
                    titmeTable_modelArrayListContent = TimeTableJsonParser.parseData(response);
                    insert(titmeTable_modelArrayListContent, positionClicked);
                }


            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

                //progressDialog.dismiss();
                try {
                    toast("Network Error");
                    swipe_refresh_layout.setRefreshing(false);
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } catch (Exception cont) {
                    cont.printStackTrace();
                }
            }
        };
    }

    public void requestDataContent(String uri, final int position) {
        positionClicked = position;

        mVolleyService = new VolleyService(mResultCallback, getActivity());
        mVolleyService.getDataVolley("GETCALL_TIMETABLES_CONTENT", uri);

    }

    private void toast(String msg) {
        StyleableToast st = new StyleableToast(getContext(), msg, Toast.LENGTH_SHORT);
        st.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        st.setTextColor(getResources().getColor(R.color.colorIcons));
        st.setIcon(R.drawable.ic_error_outline_white_24dp);

        st.setMaxAlpha();
        st.show();
        //  swipe_refresh_layout.setRefreshing(false);
    }
}

