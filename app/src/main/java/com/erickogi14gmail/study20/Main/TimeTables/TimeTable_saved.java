package com.erickogi14gmail.study20.Main.TimeTables;

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

import com.erickogi14gmail.study20.Main.Adapters.TimaTablesAdapter;
import com.erickogi14gmail.study20.Main.DB.DBOperations;
import com.erickogi14gmail.study20.Main.models.TimeTable_model;
import com.erickogi14gmail.study20.Main.utills.HidingScrollListener;
import com.erickogi14gmail.study20.Main.utills.RecyclerTouchListener;
import com.erickogi14gmail.study20.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/30/2017.
 */

public class TimeTable_saved extends Fragment {

    static View view;


    static RecyclerView.LayoutManager mLayoutManager;
    static ArrayList<TimeTable_model> timeTable_model;
    static ArrayList<TimeTable_model> temp_model;

    static TimaTablesAdapter adapter;

    static SwipeRefreshLayout swipe_refresh_layout;
    static RecyclerView lv;

    static void filter(String text) {
        try {
            ArrayList<TimeTable_model> temp = new ArrayList();
            for (TimeTable_model d : timeTable_model) {

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

    static void setRecyclerView(Context context) {
//
        DBOperations dbOperations = new DBOperations(context);
        timeTable_model = dbOperations.getTimeTablesList();
        temp_model = timeTable_model;
        try {
            if (timeTable_model.isEmpty() || timeTable_model.equals(null)) {
                swipe_refresh_layout.setRefreshing(false);
                StyleableToast st = new StyleableToast(context, "You Have No Saved TimeTables. ", Toast.LENGTH_SHORT);
                st.setBackgroundColor(Color.parseColor("#ff9040"));
                st.setTextColor(Color.WHITE);
                st.setIcon(R.drawable.ic_error_outline_white_24dp);

                st.setMaxAlpha();
                st.show();
                swipe_refresh_layout.setRefreshing(false);

            } else {


                adapter = new TimaTablesAdapter(context, timeTable_model, 1);
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
            StyleableToast st = new StyleableToast(context, "You Have No Saved  Items. ", Toast.LENGTH_SHORT);
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


        setRecyclerView(getActivity());
        lv.addOnItemTouchListener(new RecyclerTouchListener(getContext(), lv, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ReadTimeTable.class);
                intent.putExtra("TIMETABLE_ID", String.valueOf(temp_model.get(position).getTimetable_id()));

                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, final int position) {

                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.remove_unit_dialog);
                dialog.setTitle("DELETE ITEM");
                Button button_delete = (Button) dialog.findViewById(R.id.dialog_button_yes);
                Button button_keep = (Button) dialog.findViewById(R.id.dialog_button_no);

                button_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBOperations dbOperations = new DBOperations(getContext());

                        if (dbOperations.deleteTimeTable(String.valueOf(temp_model.get(position).getTimetable_id()))) {

                        }

                        dialog.dismiss();
                        timeTable_model.remove(position);

                        adapter.notifyDataSetChanged();

                        setRecyclerView(getActivity());
                        TimeTable_add.getRecyclerView_sources(getActivity());
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
