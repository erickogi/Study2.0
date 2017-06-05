package com.erickogi14gmail.study20.Main.Notifications;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.erickogi14gmail.study20.Main.Adapters.ChannelsJsonParser;
import com.erickogi14gmail.study20.Main.Adapters.NotificationsChannelListAdapter;
import com.erickogi14gmail.study20.Main.Adapters.NotificationsJsonParser;
import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.Main.DB.DBOperations;
import com.erickogi14gmail.study20.Main.models.Channel_model;
import com.erickogi14gmail.study20.Main.models.Notifications_model;
import com.erickogi14gmail.study20.Main.utills.RecyclerTouchListener;
import com.erickogi14gmail.study20.Main.volley.IResult;
import com.erickogi14gmail.study20.Main.volley.VolleyService;
import com.erickogi14gmail.study20.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

public class AddChannel extends AppCompatActivity {
    static IResult mResultCallback = null;
    static VolleyService mVolleyService;


    static RecyclerView.LayoutManager mLayoutManager;
    static ArrayList<Channel_model> channelModel;
    static NotificationsChannelListAdapter adapter;

    DBOperations dbOperations;
    SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recyclerView_vertical;
    ProgressDialog progressDialog;


    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private AdView mAdView;
    private int positionClicked = 0;

    static void getRecyclerView_sources(Context context) {
        requestDataSources(api.CHANNELS_END_POINT, context);
    }

    public static void requestDataSources(String uri, Context context) {

        mVolleyService = new VolleyService(mResultCallback, context);
        mVolleyService.getDataVolley("GETCALL_CHANNELS", uri);

    }

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
        setContentView(R.layout.activity_add_channel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVolleyCallback();
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("7A16224748E3A88A057B4F3AB8DF6BB1")
                .build();
        mAdView.loadAd(adRequest);
        progressDialog = new ProgressDialog(AddChannel.this);

        recyclerView_vertical = (RecyclerView) findViewById(R.id.recycle_view);
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipe_refresh_layout.setBackgroundResource(android.R.color.white);
        swipe_refresh_layout.setColorSchemeResources(android.R.color.white, android.R.color.holo_purple, android.R.color.white);

        swipe_refresh_layout.setRefreshing(true);

        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout.setRefreshing(true);
                getRecyclerView_sources(getApplicationContext());


            }
        });
        try {
            getRecyclerView_sources(getApplicationContext());
        } catch (Exception m) {
            Toast.makeText(this, "Experiencing some Errors :Error 100-AC", Toast.LENGTH_SHORT).show();
        }
        recyclerView_vertical.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView_vertical, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {


                int code = channelModel.get(position).getNotification_c_id();
                dbOperations = new DBOperations(getApplicationContext());
                if (dbOperations.getChannelById(String.valueOf(code))) {
                    Snackbar.make(view, "You have this Channel Already", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {


                    progressDialog.setCancelable(true);
                    progressDialog.setMessage("Downloading content.....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                    progressDialog.setIndeterminate(true);

                    progressDialog.show();


                    dbOperations = new DBOperations(getApplicationContext());
                    dbOperations.inNotificationChannels(channelModel.get(position));
                    String codeh = channelModel.get(position).getNotification_c_name().replaceAll(" ", "%20");
                    requestDataContent(api.NOTIFICATIONS_END_POINT + codeh, position);

                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    public void setRecyclerView_courses(ArrayList<Channel_model> course_modelArrayList) {
        try {
            channelModel.clear();
        } catch (Exception MN) {
            MN.printStackTrace();
        }
        channelModel = course_modelArrayList;

        adapter = new NotificationsChannelListAdapter(getApplicationContext(), course_modelArrayList);
        adapter.notifyDataSetChanged();

        recyclerView_vertical = (RecyclerView) findViewById(R.id.recycle_view);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

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

                ArrayList<Channel_model> course_modelsArrayList = new ArrayList<>();
                ArrayList<Notifications_model> contentModelArrayList;
                if (requestType.equals("GETCALL_CHANNELS")) {
                    course_modelsArrayList = ChannelsJsonParser.parseData(response);

                    setRecyclerView_courses(course_modelsArrayList);

                } else if (requestType.equals("GETCALL_CHANNELS_NOTICES")) {
                    contentModelArrayList = NotificationsJsonParser.parseData(response);
                    for (int a = 0; a < contentModelArrayList.size(); a++) {
                        insert(contentModelArrayList.get(a), positionClicked);
                    }
                    //  insert(contentModelArrayList, positionClicked);
                }


            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

                toast("Network Error");
                swipe_refresh_layout.setRefreshing(false);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

            }
        };
    }

    private void insert(Notifications_model contentModelArrayList, int positionClicked) {
        dbOperations = new DBOperations(getApplicationContext());

        if (dbOperations.inNotifications(contentModelArrayList)) {
            progressDialog.dismiss();

            //content_models.clear();

            //insertChannel(course_model.get(position));
        } else {
            //  StyleableToast st = new StyleableToast(getApplicationContext(), "Storage error 1", Toast.LENGTH_SHORT);
            toast("Storage error 1");
            progressDialog.dismiss();

        }

    }

    public void requestDataContent(String uri, final int position) {

        positionClicked = position;
        mVolleyService = new VolleyService(mResultCallback, getApplicationContext());
        mVolleyService.getDataVolley("GETCALL_CHANNELS_NOTICES", uri);

    }

    public void toast(String msg) {
        StyleableToast st = new StyleableToast(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        st.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        st.setTextColor(getResources().getColor(R.color.colorIcons));
        st.setIcon(R.drawable.ic_error_outline_white_24dp);

        st.setMaxAlpha();
        st.show();

    }
}
