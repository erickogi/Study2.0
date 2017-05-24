package com.erickogi14gmail.study20.Main.Assignments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.erickogi14gmail.study20.Main.Adapters.AssignmentsAdapter;
import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.Main.DB.DBOperations;
import com.erickogi14gmail.study20.Main.Read.ReadActivity;
import com.erickogi14gmail.study20.Main.models.Assignment_content_model;
import com.erickogi14gmail.study20.Main.models.Course_model;
import com.erickogi14gmail.study20.Main.utills.HidingScrollListener;
import com.erickogi14gmail.study20.Main.utills.RecyclerTouchListener;
import com.erickogi14gmail.study20.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/18/2017.
 */

public class fragment_saved_assignments extends Fragment {
    static View view;
    static RequestQueue queue;
    static Context context;
    static RecyclerView.LayoutManager mLayoutManager;
    static ArrayList<Course_model> course_model;
    static AssignmentsAdapter adapter;
    static ArrayList<Assignment_content_model> data_model;
    AssignmentsAdapter mainRecyclerViewAdapter;
    ArrayList<Course_model> displayedList;
    DBOperations dbOperations;
    SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recyclerView_vertical;
    ProgressDialog progressDialog;
    RecyclerView lv;
    private Toolbar mToolbar;
    private int progressBarStatus;
    private Handler progressBarHandler = new Handler();
    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    static void filter(String text) {
        ArrayList<Assignment_content_model> temp = new ArrayList();
        for (Assignment_content_model d : data_model) {
            //or use .contains(text)
            if (d.getASSIGNMENT_CODE().toLowerCase().contains(text.toLowerCase())
                    || d.getASSIGNMENT_COURSE_NAME().toLowerCase().contains(text.toLowerCase())) {
                temp.add(d);
            }

        }
        try {
            adapter.updateList(temp);
        } catch (Exception nm) {
            nm.printStackTrace();
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */

        view = inflater.inflate(R.layout.fragment_courses, container, false);


        lv = (RecyclerView) view.findViewById(R.id.recycle_view);
        swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipe_refresh_layout.setBackgroundResource(android.R.color.white);
        swipe_refresh_layout.setColorSchemeResources(android.R.color.white, android.R.color.holo_purple, android.R.color.white);

        swipe_refresh_layout.setRefreshing(true);

        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout.setRefreshing(true);
                setRecyclerView();

            }
        });


//        final FloatingActionButton fab = (FloatingActionButton)view. findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), AddCourse.class));
//            }
//        });
        setRecyclerView();
        lv.addOnItemTouchListener(new RecyclerTouchListener(getContext(), lv, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ReadActivity.class);
                intent.putExtra(api.ASSIGNMENT_ID, String.valueOf(data_model.get(position).getASSIGNMENT_ID()));

                intent.putExtra(api.POST_URL, "null");

                intent.putExtra(api.REVISION_ID, "null");

                intent.putExtra(api.COURSE_CODE, "null");
                //  Log.d("kk",data_model.get(position).getASSIGNMENT_ID())
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, final int position) {

                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.remove_unit_dialog);
                dialog.setTitle("DELETE ASSIGNMENT");
                Button button_delete = (Button) dialog.findViewById(R.id.dialog_button_yes);
                Button button_keep = (Button) dialog.findViewById(R.id.dialog_button_no);

                button_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBOperations dbOperations = new DBOperations(getContext());

                        if (dbOperations.deleteAssignment(String.valueOf(data_model.get(position).getASSIGNMENT_ID()))) {
                            // dbOperations.deleteCourse(data_model.get(position).getCOURSE_ID());
                        }

                        dialog.dismiss();
                        data_model.remove(position);
                        // notifyDataSetChanged();
                        adapter.notifyDataSetChanged();
                        setRecyclerView();
                    }
                });
                button_keep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });


                dialog.show();


            }
        }));

        lv.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                //  fab.hide();
            }

            @Override
            public void onShow() {
                //  fab.show();
            }
        });
        return view;
    }


    void setRecyclerView() {
//
        DBOperations dbOperations = new DBOperations(getContext());
        data_model = dbOperations.getAssignmentList();
        try {
            if (data_model.isEmpty() || data_model.equals(null)) {
                swipe_refresh_layout.setRefreshing(false);
                StyleableToast st = new StyleableToast(getContext(), "You Have No Saved Courses. Click the Add Button Below", Toast.LENGTH_SHORT);
                st.setBackgroundColor(Color.parseColor("#ff9040"));
                st.setTextColor(Color.WHITE);
                st.setIcon(R.drawable.ic_error_outline_white_24dp);

                st.setMaxAlpha();
                st.show();
                swipe_refresh_layout.setRefreshing(false);

            } else {


                adapter = new AssignmentsAdapter(getContext(), data_model);
                adapter.notifyDataSetChanged();


                mLayoutManager = new LinearLayoutManager(getContext());
                lv.setLayoutManager(mLayoutManager);
                lv.setItemAnimator(new DefaultItemAnimator());


                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                swipe_refresh_layout.setRefreshing(false);
            }
        } catch (Exception a) {
            a.printStackTrace();
            swipe_refresh_layout.setRefreshing(false);
            StyleableToast st = new StyleableToast(getContext(), "You Have No Saved Courses. Click the Add Button Below", Toast.LENGTH_SHORT);
            st.setBackgroundColor(Color.parseColor("#ff9040"));
            st.setTextColor(Color.WHITE);
            st.setIcon(R.drawable.ic_error_outline_white_24dp);

            st.setMaxAlpha();
            st.show();
            swipe_refresh_layout.setRefreshing(false);
        }
    }
}
