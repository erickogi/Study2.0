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
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.erickogi14gmail.study20.Main.addContent.AddCourse;
import com.erickogi14gmail.study20.Main.login.Login;
import com.erickogi14gmail.study20.Main.models.Channel_model;
import com.erickogi14gmail.study20.Main.models.Notifications_model;
import com.erickogi14gmail.study20.Main.utills.BadgeDrawable;
import com.erickogi14gmail.study20.Main.volley.IResult;
import com.erickogi14gmail.study20.Main.volley.VolleyService;
import com.erickogi14gmail.study20.R;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
//import com.mikepenz.fontawesome_typeface_library.FontAwesome;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static IResult mResultCallback = null;
    static VolleyService mVolleyService;
    boolean loggedIn;
    DBOperations dbOperations;
    Menu menup;
    private int positionClicked = 0;

    public static void requestDataSources(String uri, Context context) {

        mVolleyService = new VolleyService(mResultCallback, context);
        mVolleyService.getDataVolley("GETCALL_CHANNELS", uri);

    }

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
        SharedPreferences sharedPreferences = getSharedPreferences(api.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(api.LOGGEDIN_SHARED_PREF, false);
        loggedIn = true;
        Log.d("loginStatus",String.valueOf(loggedIn));
        if(!loggedIn){

            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
        else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            int[] results = getDbOperations();

            TextView textViewNoOfUnits = (TextView) findViewById(R.id.txt_noOfUnits);
            textViewNoOfUnits.setText("" + results[0] + " Units Downloaded");

            TextView textViewNoOfAss = (TextView) findViewById(R.id.txt_noOfAss);
            textViewNoOfAss.setText("" + results[1] + " Units Downloaded");

            TextView textViewNoOfRev = (TextView) findViewById(R.id.txt_noRev);
            textViewNoOfRev.setText("" + results[2] + " Revision items Downloaded");

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            initVolleyCallback();
            try {
                getNotices();
            } catch (Exception m) {
                m.printStackTrace();
            }

        }
    }

    private void getNotices() {

        requestDataSources(api.CHANNELS_END_POINT, getApplicationContext());


    }

    void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                ArrayList<Channel_model> course_modelsArrayList = new ArrayList<>();
                ArrayList<Notifications_model> contentModelArrayList;
                if (requestType.equals("GETCALL_CHANNELS")) {
                    course_modelsArrayList = ChannelsJsonParser.parseData(response);

                    for (int a = 0; a < course_modelsArrayList.size(); a++) {
                        int code = course_modelsArrayList.get(a).getNotification_c_id();
                        dbOperations = new DBOperations(getApplicationContext());
                        if (dbOperations.getChannelById(String.valueOf(code))) {
                            String codeh = course_modelsArrayList.get(a).getNotification_c_name().replaceAll(" ", "%20");

                            requestDataContent(api.NOTIFICATIONS_END_POINT + codeh, a);

//                        Snackbar.make(view, "You have this Course Downloaded Already", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
                        } else {

                            dbOperations = new DBOperations(getApplicationContext());
                            dbOperations.inNotificationChannels(course_modelsArrayList.get(a));
                            String codeh = course_modelsArrayList.get(a).getNotification_c_name().replaceAll(" ", "%20");
                            requestDataContent(api.NOTIFICATIONS_END_POINT + codeh, a);

                    }

                    }

                } else if (requestType.equals("GETCALL_CHANNELS_NOTICES")) {
                    contentModelArrayList = NotificationsJsonParser.parseData(response);
                    insert(contentModelArrayList, positionClicked);
                }


            }

            @Override
            public void notifyError(String requestType, VolleyError error) {


            }
        };
    }

    private void insert(ArrayList<Notifications_model> contentModelArrayList, int positionClicked) {
        dbOperations = new DBOperations(getApplicationContext());

        if (dbOperations.inNotifications(contentModelArrayList)) {

            //content_models.clear();

            // insertChannel(course_model.get(position));
        } else {
//            StyleableToast st = new StyleableToast(getApplicationContext(), "Storage error 1", Toast.LENGTH_SHORT);
//            st.setBackgroundColor(Color.parseColor("#ff9040"));
//            st.setTextColor(Color.WHITE);
//            st.setIcon(R.drawable.ic_error_outline_white_24dp);
//
//            st.setMaxAlpha();
//            st.show();
            // progressDialog.dismiss();

        }


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menup = menu;
        MenuItem itemCart = menu.getItem(0);

        BitmapDrawable iconBitmap = (BitmapDrawable) itemCart.getIcon();
        int badgeCount = 9;
        if (badgeCount > 0) {
            ActionItemBadge.update(this, menu.findItem(R.id.action_notifications), iconBitmap, ActionItemBadge.BadgeStyles.GREEN, badgeCount);
        } else {
            // ActionItemBadge.hide(menu.findItem(R.id.item_samplebadge));
        }

        //setMenu(menu);
        return true;
    }

    void setMenu(Menu menu) {
        MenuItem itemCart = menu.getItem(0);

        BitmapDrawable iconBitmap = (BitmapDrawable) itemCart.getIcon();
        LayerDrawable iconLayer = new LayerDrawable(new Drawable[]{iconBitmap});
        // setBadgeCount(this, iconLayer, "9");

        //        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        setBadgeCount(this, iconLayer, "9");
    }

    public int[] getDbOperations() {
        DBOperations dbOperations = new DBOperations(MainActivity.this);
        int no[] = new int[3];
        int Courses = dbOperations.getNoOfCourses();
        int Ass = dbOperations.getNoOfAssignments();
        int rev = dbOperations.getNoOfRevesion();

        no[0] = Courses;
        no[1] = Ass;
        no[2] = rev;


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
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            AKDialogFragment newFragment = new AKDialogFragment();
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        } else if (id == R.id.nav_send) {
            sendMail();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                    emailResultsToUser(MainActivity.this, editTextSubject.getText().toString(), editTextMessage.getText().toString(), null, null, null, null);
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


    }
}
