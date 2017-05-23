package com.erickogi14gmail.study20.Main.Revision;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.erickogi14gmail.study20.Main.Adapters.RevisionAdapter;
import com.erickogi14gmail.study20.Main.Adapters.RevisionJsonParser;
import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.Main.DB.DBOperations;
import com.erickogi14gmail.study20.Main.models.Course_model;
import com.erickogi14gmail.study20.Main.models.Revision_model;
import com.erickogi14gmail.study20.Main.utills.RecyclerTouchListener;
import com.erickogi14gmail.study20.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/22/2017.
 */

public class Revision_download extends Fragment {


    static View view;
    static RequestQueue queue;
    static Context context;
    static RecyclerView.LayoutManager mLayoutManager;
    static ArrayList<Revision_model> revision_model;
    RevisionAdapter revisionAdapter;
    ArrayList<Revision_model> displayedList;
    RevisionAdapter adapter;
    DBOperations dbOperations;
    SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recyclerView_vertical;
    ProgressDialog progressDialog;
    private Toolbar mToolbar;
    private int progressBarStatus;
    private Handler progressBarHandler = new Handler();

    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */

        view = inflater.inflate(R.layout.fragment_courses, container, false);


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
                getRecyclerView_sources();

            }
        });
        getRecyclerView_sources();
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//
//         //  SearchManager manager = (SearchManager) context.getSystemService();
//
//            SearchView search = (SearchView) view. findViewById(R.id.search_bar);
//
//          //  search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
//
//            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//
//
//
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    filter(newText);
//                    return false;
//                }
//            });
//
//        }
//

        recyclerView_vertical.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView_vertical, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {


                String code = String.valueOf(revision_model.get(position).getId());
                dbOperations = new DBOperations(getContext());
                Log.d("kl", code);
                if (dbOperations.getAssignmentById(code)) {
                    Snackbar.make(view, "You have this Revision Item Downloaded Already", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {


                    progressDialog = new ProgressDialog(view.getContext());
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Downloading content.....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    //progressDialog.setProgress(0);
                    progressDialog.setIndeterminate(true);
                    // progressDialog.setMax(100);
                    progressDialog.show();

                    progressBarStatus = 0;


                    dbOperations = new DBOperations(getContext());
                    code = code.replaceAll(" ", "%20");
                    requestDataContent(api.REVISION_END_POINT + code, position);

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

            progressDialog.dismiss();

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

    void insert(ArrayList<Revision_model> content_models, int position) {

        dbOperations = new DBOperations(getContext());

        if (dbOperations.inRevision(content_models.get(0))) {

            content_models.clear();
            progressDialog.dismiss();

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

    private void getRecyclerView_sources() {
        requestDataSources(api.REVISION_END_POINT);
    }

    void filter(String text) {
        ArrayList<Revision_model> temp = new ArrayList();
        for (Revision_model d : revision_model) {
            //or use .contains(text)
            if (d.getRevision_course_code().toLowerCase().contains(text.toLowerCase())
                    || d.getRevision_course_name().toLowerCase().contains(text.toLowerCase())) {
                temp.add(d);
            }

        }
        try {
            adapter.updateList(temp);
        } catch (Exception nm) {
            nm.printStackTrace();
        }

    }

    public void setRecyclerView_courses(ArrayList<Revision_model> revision_modelArrayList) {
        try {
            revision_model.clear();
        } catch (Exception MN) {
            MN.printStackTrace();
        }
        revision_model = revision_modelArrayList;
//        Log.d("kj",""+assignment_model.get(1).getASSIGNMENT_COURSE_NAME());

        adapter = new RevisionAdapter(getContext(), revision_modelArrayList, 0);
        adapter.notifyDataSetChanged();

        recyclerView_vertical = (RecyclerView) view.findViewById(R.id.recycle_view);

        mLayoutManager = new LinearLayoutManager(getContext());

        //      if (this.isListView) {
//
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);


//        } else {
//
//            mStaggeredLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//
//
//        }


        recyclerView_vertical.setLayoutManager(mStaggeredLayoutManager);

        recyclerView_vertical.setItemAnimator(new DefaultItemAnimator());


        recyclerView_vertical.setAdapter(adapter);
        swipe_refresh_layout.setRefreshing(false);
    }


    public void requestDataSources(String uri) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Revision_model> revision_modelArrayList = new ArrayList<>();

                        if (response != null || !response.isEmpty()) {
                            try {
                                if (!revision_modelArrayList.isEmpty()) {
                                    revision_modelArrayList.clear();
                                }
                                revision_modelArrayList.clear();
                            } catch (Exception m) {
                                m.printStackTrace();
                            }
                            revision_modelArrayList = RevisionJsonParser.parseData(response);
                            Log.d("asdd", "" + revision_modelArrayList);
                            setRecyclerView_courses(revision_modelArrayList);

                        } else {
                            swipe_refresh_layout.setRefreshing(false);
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toast("Network Error");
                        swipe_refresh_layout.setRefreshing(false);
                        // progressDialog.dismiss();


                    }
                });
        queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
        context = getContext();
    }


    public void requestDataContent(String uri, final int position) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ArrayList<Revision_model> revision_modelArrayList;


                        if (response != null || !response.isEmpty()) {


                            revision_modelArrayList = RevisionJsonParser.parseData(response);
                            // progressDialog.setProgress(50);

                            insert(revision_modelArrayList, position);


                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toast("Network Error");
                        swipe_refresh_layout.setRefreshing(false);
                        progressDialog.dismiss();
                    }
                });

        queue.add(stringRequest);


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
