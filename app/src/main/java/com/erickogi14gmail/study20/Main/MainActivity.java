package com.erickogi14gmail.study20.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.erickogi14gmail.study20.Main.Adapters.CustomAndroidGridViewAdapter;
import com.erickogi14gmail.study20.Main.Assignments.Assignments;
import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.Main.News_Api_news.News;
import com.erickogi14gmail.study20.Main.Revision.Revision;
import com.erickogi14gmail.study20.Main.addContent.AddCourse;
import com.erickogi14gmail.study20.Main.login.Login;
import com.erickogi14gmail.study20.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String[] gridViewStrings = {

            "My Notes ",

            "Assignments ",

            "News ",

            "Events ",

            "Time Tables ",


            "Revision Papers ",

            " Works ",

            "Surveys",

            "Trade",

            "Notifications ",


    };
    public static int[] gridViewImages = {

            R.drawable.notes,
//
            R.drawable.assignments,
//
            R.drawable.news,
//
            R.drawable.events,
//
            R.drawable.timetable,

//
            R.drawable.revisionexams,
//
            R.drawable.postwork,

            R.drawable.survey,

            R.drawable.trade,

            R.drawable.notifications,


    };
    boolean loggedIn;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(api.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(api.LOGGEDIN_SHARED_PREF, false);
        loggedIn = true;
        Log.d("loginStatus",String.valueOf(loggedIn));
        if(!loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
        else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            gridView = (GridView) findViewById(R.id.grid);

            gridView.setAdapter(new CustomAndroidGridViewAdapter(this, gridViewStrings, gridViewImages));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        Intent two = new Intent(MainActivity.this, AddCourse.class);
                        startActivity(two);

                    } else if (position == 2) {

                        Intent two = new Intent(MainActivity.this, News.class);
                        startActivity(two);

                    } else if (position == 1) {

                        Intent two = new Intent(MainActivity.this, Assignments.class);
                        startActivity(two);

                    } else if (position == 3) {

                        Intent two = new Intent(MainActivity.this, com.erickogi14gmail.study20.Main.Events.Events.class);
                        startActivity(two);

                    } else if (position == 5) {

                        Intent two = new Intent(MainActivity.this, Revision.class);
                        startActivity(two);

                    }
                }
            });


        }
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_account) {
            String base = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
            Log.d("basegh", base);
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_send) {

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
