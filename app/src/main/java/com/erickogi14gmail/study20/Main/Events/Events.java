package com.erickogi14gmail.study20.Main.Events;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.erickogi14gmail.study20.Main.Adapters.EventsAdapter;
import com.erickogi14gmail.study20.Main.Adapters.EventsJsonParser;
import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.Main.models.Events_model;
import com.erickogi14gmail.study20.Main.utills.RecyclerTouchListener;
import com.erickogi14gmail.study20.Main.volley.IResult;
import com.erickogi14gmail.study20.Main.volley.VolleyService;
import com.erickogi14gmail.study20.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

public class Events extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recyclerView_vertical;
    EventsAdapter adapter;
    ArrayList<Events_model> tevents_models;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        initVolleyCallback();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //  .addTestDevice("7A16224748E3A88A057B4F3AB8DF6BB1")
                .build();
        mAdView.loadAd(adRequest);

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
                getRecyclerView_sources();

            }
        });
        getRecyclerView_sources();



        recyclerView_vertical.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView_vertical, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(Events.this, EventDetails.class);
                intent.putExtra("dataposition", position);
                intent.putExtra("data", tevents_models);
                startActivity(intent);
                //intent.putStringArrayListExtra("",)
                //  startActivity(new Intent(Events.this,EventDetails.class));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

















    }

    private void getRecyclerView_sources() {
        requestDataSources(api.EVENTS_END_POINT);
    }


    public void setRecyclerView_courses(ArrayList<Events_model> events_models) {

        tevents_models = events_models;

        adapter = new EventsAdapter(getApplicationContext(), events_models);
        adapter.notifyDataSetChanged();

        recyclerView_vertical = (RecyclerView) findViewById(R.id.recycle_view);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);





        recyclerView_vertical.setLayoutManager(mStaggeredLayoutManager);

        recyclerView_vertical.setItemAnimator(new DefaultItemAnimator());


        recyclerView_vertical.setAdapter(adapter);
        swipe_refresh_layout.setRefreshing(false);
    }












    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_saved) {
            //  startActivity(new Intent(Events.this, EventDetails.class));
        } else if (id == R.id.nav_tickets) {
            //  startActivity(new Intent(Events.this, EventDetails.class));
        } else if (id == R.id.nav_add) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {
                ArrayList<Events_model> events_modelsArrayList = new ArrayList<>();


                if (requestType.equals("GETCALL_EVENTS")) {
                    events_modelsArrayList = EventsJsonParser.parseData(response);

                    setRecyclerView_courses(events_modelsArrayList);
                }


            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                try {
                    toast("Network Error");
                    swipe_refresh_layout.setRefreshing(false);
                } catch (Exception cont) {
                    cont.printStackTrace();
                }

            }
        };
    }


    public void requestDataSources(String uri) {
        mVolleyService = new VolleyService(mResultCallback, getApplicationContext());
        mVolleyService.getDataVolley("GETCALL_EVENTS", uri);


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
