package com.erickogi14gmail.study20.Main.Notifications;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.erickogi14gmail.study20.Main.Adapters.NotificationsListAdapter;
import com.erickogi14gmail.study20.Main.DB.DBOperations;
import com.erickogi14gmail.study20.Main.models.Notifications_model;
import com.erickogi14gmail.study20.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

public class NotificationsPerChannel extends AppCompatActivity {
    static ArrayList<Notifications_model> data_model;
    static RecyclerView.LayoutManager mLayoutManager;
    static RecyclerView lv;
    static SwipeRefreshLayout swipe_refresh_layout;
    private static NotificationsListAdapter adapter;
    Cursor cursor;
    DBOperations dbOperations;
    private AdView mAdView;

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_per_channel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("7A16224748E3A88A057B4F3AB8DF6BB1")
                .build();
        mAdView.loadAd(adRequest);
        lv = (RecyclerView) findViewById(R.id.recycle_view);

        Intent intent = getIntent();
        try {
            String c_name = intent.getStringExtra("channel_name");
            getSupportActionBar().setTitle(c_name);
            setRecyclerView(getApplicationContext(), c_name);
        } catch (Exception n) {

        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });


    }

    void setRecyclerView(Context context, String c_name) {
//
        DBOperations dbOperations = new DBOperations(context);
        data_model = dbOperations.getNotificationByChannel(c_name);
        try {
            if (data_model.isEmpty() || data_model.equals(null)) {

                toast("No notices");


            } else {


                adapter = new NotificationsListAdapter(context, data_model);
                adapter.notifyDataSetChanged();


                mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true);
                lv.setLayoutManager(mLayoutManager);
                lv.setItemAnimator(new DefaultItemAnimator());


                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        } catch (Exception a) {
            a.printStackTrace();

            toast("No notices");


        }
    }

    private void toast(String msg) {
        StyleableToast st = new StyleableToast(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        st.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        st.setTextColor(getResources().getColor(R.color.colorIcons));
        st.setIcon(R.drawable.ic_error_outline_white_24dp);

        st.setMaxAlpha();
        st.show();
        //  swipe_refresh_layout.setRefreshing(false);
    }

}
