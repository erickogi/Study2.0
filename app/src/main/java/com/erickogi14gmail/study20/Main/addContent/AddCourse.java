package com.erickogi14gmail.study20.Main.addContent;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.erickogi14gmail.study20.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.List;


public class AddCourse extends AppCompatActivity {


    static Fragment page;
    private static ViewPager viewPager;
    private TabLayout tabLayout;
    private AdView mAdView;

    private static void toast(String msg, Context context) {
        StyleableToast st = new StyleableToast(context, msg, Toast.LENGTH_SHORT);
        st.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

        st.setTextColor(context.getResources().getColor(R.color.colorIcons));
        st.setIcon(R.drawable.ic_error_outline_white_24dp);

        st.setMaxAlpha();
        st.show();

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
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("7A16224748E3A88A057B4F3AB8DF6BB1")
                .build();
        mAdView.loadAd(adRequest);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            SearchView search = (SearchView) findViewById(R.id.search_bar);

            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


                @Override
                public boolean onQueryTextSubmit(String query) {


                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());
                    // based on the current position you can then cast the page to the correct
                    // class and call the method:
                    CoueseList c = new CoueseList();
                    fragment_add_course a = new fragment_add_course();
                    if (viewPager.getCurrentItem() == 0 && page != null) {

                        // ((FragmentClass1)page).updateList("new item");
                        CoueseList.filter(newText);

                    } else if (viewPager.getCurrentItem() == 0 && page != null) {
                        fragment_add_course.filter(newText);
                    } else {
                        OtherformatNotes.filter(newText);
                    }

                    return false;
                }
            });

        }
//
//
    }

    public void gloabalToast(String message, int fragId, Context context) {

        Log.d("frg", "" + viewPager.getCurrentItem());
        if (viewPager.getCurrentItem() == fragId && page != null) {

            toast(message, context);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CoueseList(), "all");
        adapter.addFragment(new fragment_add_course(), "add");
        adapter.addFragment(new OtherformatNotes(), "ot");

        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {

        tabLayout.getTabAt(0).setText("Saved Courses");
        tabLayout.getTabAt(1).setText("DownLoad Courses");
        tabLayout.getTabAt(2).setText(" BOOKS ");


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
