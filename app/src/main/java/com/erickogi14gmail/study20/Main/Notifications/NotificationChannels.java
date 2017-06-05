package com.erickogi14gmail.study20.Main.Notifications;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.erickogi14gmail.study20.Main.Adapters.NotificationsChannelListAdapter;
import com.erickogi14gmail.study20.Main.DB.DBOperations;
import com.erickogi14gmail.study20.Main.models.Channel_model;
import com.erickogi14gmail.study20.Main.utills.RecyclerTouchListener;
import com.erickogi14gmail.study20.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

public class NotificationChannels extends AppCompatActivity {
    static RecyclerView.LayoutManager mLayoutManager;
    static ArrayList<Channel_model> data_model;
    static RecyclerView lv;
    static SwipeRefreshLayout swipe_refresh_layout;
    private static NotificationsChannelListAdapter adapter;
    Cursor cursor;
    DBOperations dbOperations = new DBOperations(getBaseContext());
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
        setContentView(R.layout.activity_notification_channels);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                // .addTestDevice("7A16224748E3A88A057B4F3AB8DF6BB1")
                .build();
        mAdView.loadAd(adRequest);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationChannels.this, AddChannel.class));
            }
        });

        lv = (RecyclerView) findViewById(R.id.recycle_view);
        lv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), lv, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(NotificationChannels.this, NotificationsPerChannel.class);
                intent.putExtra("channel_name", data_model.get(position).getNotification_c_name());
                DBOperations dbOperations = new DBOperations(getApplicationContext());
                dbOperations.updateToRead(1, data_model.get(position).getNotification_c_name());
                startActivity(intent);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


//        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
//        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
//        swipe_refresh_layout.setBackgroundResource(android.R.color.white);
//        swipe_refresh_layout.setColorSchemeResources(android.R.color.white, android.R.color.holo_purple, android.R.color.white);
//
//        swipe_refresh_layout.setRefreshing(true);
//
//        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipe_refresh_layout.setRefreshing(true);
//                setRecyclerView(getApplicationContext());
//
//            }
//        });
        try {
            setRecyclerView(getApplicationContext());
        } catch (Exception m) {
            Toast.makeText(this, "Experiencing some Errors :Error 200-NC", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            setRecyclerView(getApplicationContext());
        } catch (Exception m) {
            Toast.makeText(this, "Experiencing some Errors :Error 200-NC", Toast.LENGTH_SHORT).show();
        }
    }

    void setRecyclerView(Context context) {
//
        DBOperations dbOperations = new DBOperations(context);
        data_model = dbOperations.getChannelsList();
        try {
            if (data_model.isEmpty() || data_model.equals(null)) {
                toast("You Have No Saved Channels. Click the Add Button Below");


            } else {


                adapter = new NotificationsChannelListAdapter(context, data_model);
                adapter.notifyDataSetChanged();


                mLayoutManager = new LinearLayoutManager(context);
                lv.setLayoutManager(mLayoutManager);
                lv.setItemAnimator(new DefaultItemAnimator());


                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        } catch (Exception a) {
            a.printStackTrace();
            toast("You Have No Saved Channels. Click the Add Button Below");

        }
    }

    public void toast(String msg) {
        StyleableToast st = new StyleableToast(NotificationChannels.this, msg, Toast.LENGTH_SHORT);
        st.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        st.setTextColor(getResources().getColor(R.color.colorIcons));
        st.setIcon(R.drawable.ic_error_outline_white_24dp);

        st.setMaxAlpha();
        st.show();

    }
}
