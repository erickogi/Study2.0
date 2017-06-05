package com.erickogi14gmail.study20.Main.addContent;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.erickogi14gmail.study20.Main.Adapters.AddRecyclerViewAdapter;
import com.erickogi14gmail.study20.Main.Adapters.ContentJsonParser;
import com.erickogi14gmail.study20.Main.Adapters.JsonParser;
import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.Main.DB.DBOperations;
import com.erickogi14gmail.study20.Main.models.Content_model;
import com.erickogi14gmail.study20.Main.models.Course_model;
import com.erickogi14gmail.study20.Main.utills.RecyclerTouchListener;
import com.erickogi14gmail.study20.Main.volley.IResult;
import com.erickogi14gmail.study20.Main.volley.VolleyService;
import com.erickogi14gmail.study20.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/17/2017.
 */

public class fragment_add_course extends Fragment {
    static View view;

    static RecyclerView.LayoutManager mLayoutManager;
    static ArrayList<Course_model> course_model;
    static ArrayList<Course_model> temp_model;
    static AddRecyclerViewAdapter adapter;
    static IResult mResultCallback = null;
    static VolleyService mVolleyService;
    DBOperations dbOperations;
    SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recyclerView_vertical;
    ProgressDialog progressDialog;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private int positionClicked = 0;

    static void filter(String text) {
        try {
        ArrayList<Course_model> temp = new ArrayList();
        for (Course_model d : course_model) {
            //or use .contains(text)
            if (d.getCOURSE_ID().toLowerCase().contains(text.toLowerCase()) || d.getCOURSE_TITLE().toLowerCase().contains(text.toLowerCase())) {
                temp.add(d);
            }

        }
            temp_model = temp;
            adapter.updateList(temp);
        } catch (Exception nm) {
            nm.printStackTrace();
        }

    }

    static void getRecyclerView_sources(Context context) {
        requestDataSources(api.COURSES_END_POINT, context);
    }

    public static void requestDataSources(String uri, Context context) {

        mVolleyService = new VolleyService(mResultCallback, context);
        mVolleyService.getDataVolley("GETCALL_COURSES", uri);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */

        view = inflater.inflate(R.layout.fragment_courses, container, false);
        initVolleyCallback();
        progressDialog = new ProgressDialog(view.getContext());

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


                String code = temp_model.get(position).getCOURSE_ID();
                dbOperations = new DBOperations(getContext());
                if (dbOperations.getCourseById(code)) {
                    Snackbar.make(view, "You have this Course Downloaded Already", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {



                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Downloading content.....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                    progressDialog.setIndeterminate(true);

                    progressDialog.show();




                    dbOperations = new DBOperations(getContext());
                    code = code.replaceAll(" ", "%20");
                    requestDataContent(api.CONTENT_END_POINT + code, position);

                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    void insertCourse(Course_model data) {
        dbOperations = new DBOperations(getContext());

        if (dbOperations.inCourse(data)) {


            getRecyclerView_sources(getActivity());
            progressDialog.dismiss();
            CoueseList.setRecyclerView(getActivity());

            StyleableToast st = new StyleableToast(getContext(), "Saved Successfully", Toast.LENGTH_SHORT);
            st.setBackgroundColor(Color.parseColor("#ff9040"));
            st.setTextColor(Color.WHITE);


            st.setMaxAlpha();
            st.show();

        } else {
            StyleableToast st = new StyleableToast(getContext(), "Storage error 2", Toast.LENGTH_SHORT);
            st.setBackgroundColor(Color.parseColor("#ff9040"));
            st.setTextColor(Color.WHITE);
            st.setIcon(R.drawable.ic_error_outline_white_24dp);

            st.setMaxAlpha();
            st.show();
            progressDialog.dismiss();

        }

    }

    void insert(ArrayList<Content_model> content_models, int position) {

        dbOperations = new DBOperations(getContext());

        if (dbOperations.in(content_models)) {

            content_models.clear();

            insertCourse(course_model.get(position));
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

    public void setRecyclerView_courses(ArrayList<Course_model> course_modelArrayList) {
        try {
            course_model.clear();
        } catch (Exception MN) {
            MN.printStackTrace();
        }
        course_model = course_modelArrayList;
        temp_model = course_model;

        adapter = new AddRecyclerViewAdapter(getContext(), course_modelArrayList, 0);
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

                ArrayList<Course_model> course_modelsArrayList = new ArrayList<>();
                ArrayList<Content_model> contentModelArrayList;
                if (requestType.equals("GETCALL_COURSES")) {
                    course_modelsArrayList = JsonParser.parseData(response);

                    setRecyclerView_courses(course_modelsArrayList);
                } else if (requestType.equals("GETCALL_COURSES_CONTENT")) {
                    contentModelArrayList = ContentJsonParser.parseData(response);
                    try {


                        insert(contentModelArrayList, positionClicked);
                    } catch (Exception M) {
                        // Toast.makeText(this, "Experiencing some Errors :Error 200-NC", Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
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
        mVolleyService.getDataVolley("GETCALL_COURSES_CONTENT", uri);

    }

    public void toast(String msg) {
        StyleableToast st = new StyleableToast(getContext(), msg, Toast.LENGTH_SHORT);
        st.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        st.setTextColor(getResources().getColor(R.color.colorIcons));
        st.setIcon(R.drawable.ic_error_outline_white_24dp);

        st.setMaxAlpha();
        st.show();

    }


}
