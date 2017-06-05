package com.erickogi14gmail.study20.Main.Assignments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.erickogi14gmail.study20.Main.Adapters.AssignmentDownloadAdapter;
import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.Main.DB.DBOperations;
import com.erickogi14gmail.study20.Main.Read.ReadActivity;
import com.erickogi14gmail.study20.Main.models.Assignment_content_model;
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

    static RecyclerView.LayoutManager mLayoutManager;

    static AssignmentDownloadAdapter adapter;
    static ArrayList<Assignment_content_model> data_model;
    static ArrayList<Assignment_content_model> temp_model;


    static SwipeRefreshLayout swipe_refresh_layout;

    static RecyclerView lv;



    static void filter(String text) {
        try {
        ArrayList<Assignment_content_model> temp = new ArrayList();
        for (Assignment_content_model d : data_model) {
            //or use .contains(text)
            if (d.getASSIGNMENT_CODE().toLowerCase().contains(text.toLowerCase())
                    || d.getASSIGNMENT_COURSE_NAME().toLowerCase().contains(text.toLowerCase())
                    || d.getASSIGNMENT_NAME().toLowerCase().contains(text.toLowerCase())) {
                temp.add(d);
            }

        }
            temp_model = temp;
            adapter.updateList(temp);
        } catch (Exception nm) {
            nm.printStackTrace();
        }

    }

    static void setRecyclerView(Context context) {
//
        DBOperations dbOperations = new DBOperations(context);
        data_model = dbOperations.getAssignmentList();
        temp_model = data_model;
        try {
            if (data_model.isEmpty() || data_model.equals(null)) {
                swipe_refresh_layout.setRefreshing(false);
                StyleableToast st = new StyleableToast(context, "You Have No Saved Courses. Click the Add Button Below", Toast.LENGTH_SHORT);
                st.setBackgroundColor(Color.parseColor("#ff9040"));
                st.setTextColor(Color.WHITE);
                st.setIcon(R.drawable.ic_error_outline_white_24dp);

                st.setMaxAlpha();
                st.show();
                swipe_refresh_layout.setRefreshing(false);

            } else {


                adapter = new AssignmentDownloadAdapter(context, data_model, 1);
                adapter.notifyDataSetChanged();


                mLayoutManager = new LinearLayoutManager(context);
                lv.setLayoutManager(mLayoutManager);
                lv.setItemAnimator(new DefaultItemAnimator());


                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                swipe_refresh_layout.setRefreshing(false);
            }
        } catch (Exception a) {
            a.printStackTrace();
            swipe_refresh_layout.setRefreshing(false);
            StyleableToast st = new StyleableToast(context, "You Have No Saved Courses. Click the Add Button Below", Toast.LENGTH_SHORT);
            st.setBackgroundColor(Color.parseColor("#ff9040"));
            st.setTextColor(Color.WHITE);
            st.setIcon(R.drawable.ic_error_outline_white_24dp);

            st.setMaxAlpha();
            st.show();
            swipe_refresh_layout.setRefreshing(false);
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
                setRecyclerView(getActivity());

            }
        });


//        final FloatingActionButton fab = (FloatingActionButton)view. findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), AddCourse.class));
//            }
//        });
        setRecyclerView(getActivity());
        temp_model = data_model;
        lv.addOnItemTouchListener(new RecyclerTouchListener(getContext(), lv, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                try {


                Intent intent = new Intent(getActivity(), ReadActivity.class);
                    intent.putExtra(api.ASSIGNMENT_ID, String.valueOf(temp_model.get(position).getASSIGNMENT_ID()));

                intent.putExtra(api.POST_URL, "null");

                intent.putExtra(api.REVISION_ID, "null");

                intent.putExtra(api.COURSE_CODE, "null");
                //  Log.d("kk",data_model.get(position).getASSIGNMENT_ID())
                    startActivity(intent);
                } catch (Exception m) {
                    m.printStackTrace();
                }


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

                        if (dbOperations.deleteAssignment(String.valueOf(temp_model.get(position).getASSIGNMENT_ID()))) {
                            // dbOperations.deleteCourse(data_model.get(position).getCOURSE_ID());
                        }

                        dialog.dismiss();
                        data_model.remove(position);
                        // notifyDataSetChanged();
                        adapter.notifyDataSetChanged();

                        setRecyclerView(getActivity());
                        fragment_download_assignment.getRecyclerView_sources(getActivity());
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
}
