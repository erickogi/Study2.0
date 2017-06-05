package com.erickogi14gmail.study20.Main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.erickogi14gmail.study20.Main.Adapters.ChannelsJsonParser;
import com.erickogi14gmail.study20.Main.Adapters.NotificationsJsonParser;
import com.erickogi14gmail.study20.Main.Assignments.Assignments;
import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.Main.DB.DBOperations;
import com.erickogi14gmail.study20.Main.News_Api_news.News;
import com.erickogi14gmail.study20.Main.Notifications.NotificationChannels;
import com.erickogi14gmail.study20.Main.Revision.Revision;
import com.erickogi14gmail.study20.Main.TimeTables.TimeTables;
import com.erickogi14gmail.study20.Main.addContent.AddCourse;
import com.erickogi14gmail.study20.Main.login.Login;
import com.erickogi14gmail.study20.Main.models.Channel_model;
import com.erickogi14gmail.study20.Main.models.Notifications_model;
import com.erickogi14gmail.study20.Main.utills.BadgeDrawable;
import com.erickogi14gmail.study20.Main.volley.IResult;
import com.erickogi14gmail.study20.Main.volley.VolleyService;
import com.erickogi14gmail.study20.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
//import com.mikepenz.fontawesome_typeface_library.FontAwesome;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static IResult mResultCallback = null;
    static VolleyService mVolleyService;
    boolean admChanel;
    boolean loggedIn;
    String email;
    DBOperations dbOperations;
    Menu menup;
    SharedPreferences sharedPreferences;
    private int positionClicked = 0;
    private AdView mAdView;

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();

        icon.setDrawableByLayerId(R.id.ic_badge, badge);
        Log.d("kic", "" + icon);


    }

    public static void requestDataSources(String uri, Context context) {

        mVolleyService = new VolleyService(mResultCallback, context);
        mVolleyService.getDataVolley("GETCALL_ADMIN_CHANNEL", uri);

    }

    public static void emailResultsToUser(Activity activity,
                                          String subject,
                                          String bodyText,
                                          String chooserTitle,
                                          String[] toRecipients,
                                          String[] ccRecipients,
                                          String[] bccRecipients) {
        Intent mailIntent = new Intent();
        mailIntent.setAction(Intent.ACTION_SEND);
        mailIntent.setType("message/rfc822");
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mailIntent.putExtra(Intent.EXTRA_TEXT, bodyText);

        if (toRecipients != null) {
            mailIntent.putExtra(Intent.EXTRA_EMAIL, toRecipients);
        }
        if (ccRecipients != null) {
            mailIntent.putExtra(Intent.EXTRA_CC, ccRecipients);
        }
        if (bccRecipients != null) {
            mailIntent.putExtra(Intent.EXTRA_BCC, bccRecipients);
        }
        if (chooserTitle == null) chooserTitle = "Send Email";
        activity.startActivity(Intent.createChooser(mailIntent, chooserTitle));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(api.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(api.LOGGEDIN_SHARED_PREF, false);
        email = sharedPreferences.getString(api.EMAIL_SHARED_PREF, "@gmail.com");
        // loggedIn = true;
        Log.d("loginStatus",String.valueOf(loggedIn));
        if(!loggedIn){

            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
        else {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setItems();
            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder()
                    //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    // .addTestDevice("7A16224748E3A88A057B4F3AB8DF6BB1")
                    .build();
            mAdView.loadAd(adRequest);

//            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//            drawer.setDrawerListener(toggle);
//            toggle.syncState();
//
//            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//            navigationView.setNavigationItemSelectedListener(this);
            initVolleyCallback();
            try {
                getNotices();
            } catch (Exception m) {
                m.printStackTrace();
            }

        }
    }

    void setItems() {
        int[] results = getDbOperations();


        TextView textViewNoOfUnits = (TextView) findViewById(R.id.txt_noOfUnits);
        textViewNoOfUnits.setText("" + results[0] + " Units Downloaded");

        TextView textViewNoOfAss = (TextView) findViewById(R.id.txt_noOfAss);
        textViewNoOfAss.setText("" + results[1] + " Units Downloaded");

        TextView textViewNoOfRev = (TextView) findViewById(R.id.txt_noRev);
        textViewNoOfRev.setText("" + results[2] + " Revision items Downloaded");

        TextView textViewNoOfTT = (TextView) findViewById(R.id.txt_noOfTT);
        textViewNoOfTT.setText("" + results[3] + " TimeTables items Downloaded");

    }

    void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                ArrayList<Channel_model> course_modelsArrayList = new ArrayList<>();
                ArrayList<Notifications_model> contentModelArrayList;


                if (requestType.equals("GETCALL_ADMIN_CHANNEL")) {
                    course_modelsArrayList = ChannelsJsonParser.parseData(response);
                    dbOperations = new DBOperations(getApplicationContext());
                    if (dbOperations.inNotificationChannels(course_modelsArrayList.get(0))) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(api.ADMINCHANNEL_SHARED_PREF, true);
                        editor.apply();
                    }
                    String codeh = course_modelsArrayList.get(0).getNotification_c_name().replaceAll(" ", "%20");

                    requestDataContent(api.NOTIFICATIONS_END_POINT + codeh, 0);







                } else if (requestType.equals("GETCALL_CHANNELS_NOTICES")) {
                    contentModelArrayList = NotificationsJsonParser.parseData(response);
                    for (int a = 0; a < contentModelArrayList.size(); a++) {

                        insert(contentModelArrayList.get(a), positionClicked);
                    }
                    for (int a = 0; a < contentModelArrayList.size(); a++) {
                        Log.d("notideddf", "" + response);
                    }
                }


            }

            @Override
            public void notifyError(String requestType, VolleyError error) {


            }
        };
    }

    private void getNotices() {
        admChanel = sharedPreferences.getBoolean(api.ADMINCHANNEL_SHARED_PREF, false);
        if (admChanel) {

            ArrayList<Channel_model> chan = getChannelsInDb();


            requestDataContent(api.NOTIFICATIONS_END_POINT, 0);


        } else {
            requestDataSources(api.CHANNELS_END_POINT + 1, getApplicationContext());
        }


    }

    ArrayList<Channel_model> getChannelsInDb() {
        DBOperations dbOperations = new DBOperations(MainActivity.this);
        ArrayList<Channel_model> channelModels = dbOperations.getChannelsList();
        return channelModels;
    }

    private int insert(Notifications_model contentModelArrayList, int positionClicked) {
        dbOperations = new DBOperations(getApplicationContext());
        int st = 0;
//        Log.d("notide",""+contentModelArrayList.get(1));
        if (dbOperations.inNotifications(contentModelArrayList)) {
            st = 1;
            setMenuItem();

        } else {
            st = 1;
        }
        return st;

    }

    public void requestDataContent(String uri, final int position) {

        positionClicked = position;
        mVolleyService = new VolleyService(mResultCallback, getApplicationContext());
        mVolleyService.getDataVolley("GETCALL_CHANNELS_NOTICES", uri);

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

    void setMenuItem() {
        MenuItem itemCart = menup.getItem(0);
        //getDbOperations();
        getNotices();
        BitmapDrawable iconBitmap = (BitmapDrawable) itemCart.getIcon();
        DBOperations dbOperations = new DBOperations(MainActivity.this);
        int badgeCount = dbOperations.getNoOfNotificationsUnread(0);
        if (badgeCount > 0) {
            ActionItemBadge.update(this, menup.findItem(R.id.action_notifications), iconBitmap, ActionItemBadge.BadgeStyles.GREEN, badgeCount);
        } else {

            ActionItemBadge.update(this, menup.findItem(R.id.action_notifications), iconBitmap, ActionItemBadge.BadgeStyles.GREEN, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menup = menu;
        setMenuItem();

        //setMenu(menu);
        return true;
    }

    public int[] getDbOperations() {
        DBOperations dbOperations = new DBOperations(MainActivity.this);
        int no[] = new int[4];
        int Courses = dbOperations.getNoOfCourses();
        int Ass = dbOperations.getNoOfAssignments();
        int rev = dbOperations.getNoOfRevesion();
        int tt = dbOperations.getNoOfTimeTables();

        no[0] = Courses;
        no[1] = Ass;
        no[2] = rev;
        no[3] = tt;


        return no;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notifications) {
            startActivity(new Intent(MainActivity.this, NotificationChannels.class));
            return true;
        } else if (id == R.id.action_share) {
            Intent in = new Intent();
            in.setAction(Intent.ACTION_SEND);
            in.putExtra(Intent.EXTRA_TEXT, " STUDY APP:" + api.APP_SHARE_LINK);
            in.setType("text/plain");
            startActivity(in);

        } else if (id == R.id.action_contact) {
            sendMail();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {

        } else if (id == R.id.nav_settings) {

        }

        //else if (id == R.id.nav_manage) {

        // }
        else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_help) {
        } else if (id == R.id.nav_send) {
            sendMail();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setItems();
        setMenuItem();
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

    public void sendMail() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_sendmail);
        dialog.setTitle("Contact Developer");
        final EditText editTextSubject = (EditText) dialog.findViewById(R.id.dialog_subject);
        final EditText editTextMessage = (EditText) dialog.findViewById(R.id.dialog_message);
        Button buttonCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button buttonSend = (Button) dialog.findViewById(R.id.btn_send);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextMessage.getText().toString().isEmpty() || editTextSubject.getText().toString().isEmpty()) {
                    toast("Fill all fields");
                } else {
                    String[] recepient = {"erickogi14@gmail.com"};
                    emailResultsToUser(MainActivity.this,
                            editTextSubject.getText().toString(),
                            editTextMessage.getText().toString(),
                            "Contact Developer",
                            recepient,
                            null,
                            null);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();

    }

    private void toast(String msg) {
        StyleableToast st = new StyleableToast(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        st.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        st.setTextColor(getResources().getColor(R.color.colorIcons));
        st.setIcon(R.drawable.ic_error_outline_white_24dp);

        st.setMaxAlpha();
        st.show();

    }

    public void openMyNotes(View view) {
        Intent two = new Intent(MainActivity.this, AddCourse.class);
        startActivity(two);
    }

    public void openAssignments(View view) {
        Intent two = new Intent(MainActivity.this, Assignments.class);
        startActivity(two);
    }

    public void openNews(View view) {
        Intent two = new Intent(MainActivity.this, News.class);
        startActivity(two);
    }

    public void openEvents(View view) {
        Intent two = new Intent(MainActivity.this, com.erickogi14gmail.study20.Main.Events.Events.class);
        startActivity(two);
    }

    public void openRevision(View view) {
        Intent two = new Intent(MainActivity.this, Revision.class);
        startActivity(two);
    }

    public void openTimetable(View view) {

        Intent two = new Intent(MainActivity.this, TimeTables.class);
        startActivity(two);
    }
}
