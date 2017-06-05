package com.erickogi14gmail.study20.Main.Read;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.Main.DB.DBOperations;
import com.erickogi14gmail.study20.Main.models.Assignment_content_model;
import com.erickogi14gmail.study20.Main.models.Chapters;
import com.erickogi14gmail.study20.Main.models.Revision_model;
import com.erickogi14gmail.study20.Main.utills.TouchyWebView;
import com.erickogi14gmail.study20.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    TouchyWebView webView;
    DBOperations dbOperations = new DBOperations(this);
    String cont[];
    FloatingActionButton fabP, fab;
    Toolbar toolbar;
    private String code=null;
    private String assId = "";
    private String html;
    private String url;
    private String rvId;
    private int nav_id = 0;
    private  String nav_items[];
    private AdView mAdView;

    protected void setNavigationItems(String[] items) {
        nav_items=items;
        final Menu menu = navigationView.getMenu();
        for (int a = 0; a < items.length; a++) {


            menu.add(items[a]);

        }


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
        setContentView(R.layout.activity_read);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                // .addTestDevice("7A16224748E3A88A057B4F3AB8DF6BB1")
                .build();
        mAdView.loadAd(adRequest);


         fab= (FloatingActionButton) findViewById(R.id.fab_next);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int to=nav_id+1;
                if(to<nav_items.length){
                    getSupportActionBar().setTitle(nav_items[to]);
                    setWebView(dbOperations.getChapterContentByChapterByTitle(code,nav_items[to]));
                    nav_id=to;
                }
            }
        });

        fabP = (FloatingActionButton) findViewById(R.id.fab_prev);
        fabP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int to=nav_id-1;
                if(to<nav_items.length&&to>=0){
                    getSupportActionBar().setTitle(nav_items[to]);
                    setWebView(dbOperations.getChapterContentByChapterByTitle(code,nav_items[to]));
                    nav_id=to;
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        webView = (TouchyWebView) findViewById(R.id.webView);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        try {
        Intent intent = getIntent();
        code = intent.getStringExtra(api.COURSE_CODE);
        assId = intent.getStringExtra(api.ASSIGNMENT_ID);
        url = intent.getStringExtra(api.POST_URL);
        rvId = intent.getStringExtra(api.REVISION_ID);

        if (assId.equals("null") && url.equals("null") && rvId.equals("null")) {

            ArrayList<String> list = new ArrayList<>();
            ArrayList<Chapters> chapters = dbOperations.getChaptersByCourse(code);
            for (int count = 0; count < chapters.size(); count++) {
                list.add(chapters.get(count).getChapter_title());

            }
            String[] Arr = new String[list.size()];
            Arr = list.toArray(Arr);
            setNavigationItems(Arr);

            cont = dbOperations.getChapterContentByChapterByCourse(code, "1");

            html = cont[0];
            getSupportActionBar().setTitle(cont[1]);


            webView.setOnScrollChangedCallback(new TouchyWebView.OnScrollChangedCallback() {
                @Override
                public void onScrollChange(WebView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY && scrollY > 0) {

                        fab.hide();
                        fabP.hide();
                        //  Toast.makeText(ReadActivity.this, "Scrolled", Toast.LENGTH_SHORT).show();
                    }
                    if (scrollY < oldScrollY) {
                        if (nav_id >= 1) {
                            fabP.show();
                        }
                        if (nav_id < nav_items.length - 1) {
                            fab.show();
                        }

                        //  Toast.makeText(ReadActivity.this, "Scrolled", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            setWebView(html);
            nav_id = 0;
        } else if (code.equals("null") && url.equals("null") && rvId.equals("null")) {
            ArrayList<Assignment_content_model> data = dbOperations.getAssignmentListById(assId);
            html = data.get(0).getASSIGNMENT_CONTENT();
            getSupportActionBar().setTitle(data.get(0).getASSIGNMENT_NAME());

            setWebView(html);
            nav_id = 0;
        } else if (code.equals("null") && url.equals("null") && assId.equals("null")) {
            ArrayList<Revision_model> data = dbOperations.getRevisionListById(rvId);
            html = data.get(0).getRevision_content();
            getSupportActionBar().setTitle(data.get(0).getRevision_title());

            setWebView(html);
            nav_id = 0;
        } else {
            html = url;
            webView.loadUrl(html);

            webView.getSettings().setJavaScriptEnabled(true);
        }


        } catch (Exception m) {
            Toast.makeText(this, "Experiencing some Errors :Error 2100-RA", Toast.LENGTH_SHORT).show();
        }

    }

private void setWebView(String html){
    webView.getSettings().setJavaScriptEnabled(true);


//    webView.getSettings().setUseWideViewPort(true);
//    webView.getSettings().setLoadWithOverviewMode(true);
//    webView.getSettings().setAllowFileAccess(true);
   // webView.getSettings().setBuiltInZoomControls(true);
    webView.getSettings().setDisplayZoomControls(true);
   // webView.getSettings().setDefaultFontSize(25);
    webView.loadDataWithBaseURL(null,null,null,null,null);
    webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);


    webView.setWebChromeClient(new WebChromeClient() {


    });
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
        //getMenuInflater().inflate(R.menu.read, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
      //  String courseTilte=item.toString()

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
        String topicName = item.getTitle().toString();

        nav_id=item.getItemId();
        getSupportActionBar().setTitle(topicName);


        setWebView( dbOperations.getChapterContentByChapterByTitle(code,topicName));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
