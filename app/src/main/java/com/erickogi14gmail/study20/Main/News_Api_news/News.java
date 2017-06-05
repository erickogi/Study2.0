package com.erickogi14gmail.study20.Main.News_Api_news;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class News extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static String category = "";
    static boolean isListView;
    InterstitialAd mInterstitialAd;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        AdRequest adRequest = new AdRequest.Builder()
                // .addTestDevice("7A16224748E3A88A057B4F3AB8DF6BB1")
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });


//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        isListView = true;
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
    private void toggle() {
        localNews fragment_local_news = new localNews();
        internationalNews fragment_international_news = new internationalNews();
        MenuItem item = menu.findItem(R.id.action_toggle);
        if (isListView) {


            item.setIcon(R.drawable.ic_list_white_24dp);
            item.setTitle("Show as list");
            isListView = false;


            fragment_local_news.setLayout(isListView);
            fragment_international_news.setLayout(isListView);
        } else {


            item.setIcon(R.drawable.ic_grid_on_white_24dp);
            item.setTitle("Show as grid");
            isListView = true;

            fragment_local_news.setLayout(isListView);
            fragment_international_news.setLayout(isListView);
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
        // getMenuInflater().inflate(R.menu.news, menu);
        // this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_toggle) {
            // toggle();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void start() {
        localNews fragment_local_news = new localNews();
        internationalNews fragment_international_news = new internationalNews();
        //fragment_local_news.start();
        fragment_international_news.start();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent = new Intent(News.this, Categories.class);
        if (id == R.id.nav_all) {
            // category = "";
            // start();

//        } else if (id == R.id.nav_tech) {
//            category = api.KEY_CATEGORY_TECH;
//            start();
//
//
//        } else if (id == R.id.nav_business) {
//            category = api.KEY_CATEGORY_BUSINESS;
//            start();
//
//
//        } else if (id == R.id.nav_entertainment) {
//            category = api.KEY_CATEGORY_ENTERTAINMENT;
//            start();
//
//        } else if (id == R.id.nav_politics) {
//            category = api.KEY_CATEGORY_POLITICS;
//            start();
//
//        } else if (id == R.id.nav_music) {
//            category = api.KEY_CATEGORY_MUSIC;
//            start();
//
//        } else if (id == R.id.nav_science) {
//            category = api.KEY_CATEGORY_SCIENCE;
//            start();
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_sports) {
//            category = api.KEY_CATEGORY_SPORTS;
//            start();
        } else if (id == R.id.nav_tech) {
            intent.putExtra(api.KEY_CATEGORY_INTENT, api.KEY_CATEGORY_TECH);
            intent.putExtra(api.KEY_CATEGORY_LABEL, "Technology");
            startActivity(intent);
        } else if (id == R.id.nav_business) {
            intent.putExtra(api.KEY_CATEGORY_INTENT, api.KEY_CATEGORY_BUSINESS);
            intent.putExtra(api.KEY_CATEGORY_LABEL, "Business");
            startActivity(intent);
        } else if (id == R.id.nav_entertainment) {
            intent.putExtra(api.KEY_CATEGORY_INTENT, api.KEY_CATEGORY_ENTERTAINMENT);
            intent.putExtra(api.KEY_CATEGORY_LABEL, "Entertainment");
            startActivity(intent);

        } else if (id == R.id.nav_politics) {
            intent.putExtra(api.KEY_CATEGORY_INTENT, api.KEY_CATEGORY_POLITICS);
            intent.putExtra(api.KEY_CATEGORY_LABEL, "Political");
            startActivity(intent);

        } else if (id == R.id.nav_music) {
            intent.putExtra(api.KEY_CATEGORY_INTENT, api.KEY_CATEGORY_MUSIC);
            intent.putExtra(api.KEY_CATEGORY_LABEL, "Music");
            startActivity(intent);

        } else if (id == R.id.nav_science) {
            intent.putExtra(api.KEY_CATEGORY_INTENT, api.KEY_CATEGORY_SCIENCE);
            intent.putExtra(api.KEY_CATEGORY_LABEL, "Science/Nature");
            startActivity(intent);

        } else if (id == R.id.nav_sports) {
            intent.putExtra(api.KEY_CATEGORY_INTENT, api.KEY_CATEGORY_SPORTS);
            intent.putExtra(api.KEY_CATEGORY_LABEL, "Sports");
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new internationalNews(), "in");
        adapter.addFragment(new localNews(), "lo");

        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {

        tabLayout.getTabAt(1).setText("LOCAL");
        tabLayout.getTabAt(0).setText("INTERNATIONAL");


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
