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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.erickogi14gmail.study20.Main.Adapters.EventsAdapter;
import com.erickogi14gmail.study20.Main.Adapters.EventsJsonParser;
import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.Main.models.Events_model;
import com.erickogi14gmail.study20.Main.utills.RecyclerTouchListener;
import com.erickogi14gmail.study20.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

public class Events extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static RequestQueue queue;
    static RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recyclerView_vertical;
    EventsAdapter adapter;
    ArrayList<Events_model> tevents_models;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//            }
//        });

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//
//          //  SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//
//          //  SearchView search = (SearchView) view. findViewById(R.id.search_bar);
//
//           // search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
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

//    void filter(String text) {
//        ArrayList<Events_model> temp = new ArrayList();
//        for (Course_model d : course_model) {
//            //or use .contains(text)
//            if (d.getCOURSE_ID().toLowerCase().contains(text.toLowerCase())
//                    || d.getCOURSE_TITLE().toLowerCase().contains(text.toLowerCase())) {
//                temp.add(d);
//            }
//
//        }
//        try {
//            adapter.updateList(temp);
//        } catch (Exception nm) {
//            nm.printStackTrace();
//        }
//
//    }

    public void setRecyclerView_courses(ArrayList<Events_model> events_models) {

        tevents_models = events_models;

        adapter = new EventsAdapter(getApplicationContext(), events_models);
        adapter.notifyDataSetChanged();

        recyclerView_vertical = (RecyclerView) findViewById(R.id.recycle_view);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

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

    public void requestDataSources(String uri) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Events_model> events_modelsArrayList = new ArrayList<>();

                        if (response != null || !response.isEmpty()) {
                            try {
                                if (!events_modelsArrayList.isEmpty()) {
                                    events_modelsArrayList.clear();
                                }
                                events_modelsArrayList.clear();
                            } catch (Exception m) {
                                m.printStackTrace();
                            }
                            Log.d("klm", "" + response);
                            events_modelsArrayList = EventsJsonParser.parseData(response);

                            setRecyclerView_courses(events_modelsArrayList);

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
        queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
        //context = getContext();
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
