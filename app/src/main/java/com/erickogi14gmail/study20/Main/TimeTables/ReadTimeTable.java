package com.erickogi14gmail.study20.Main.TimeTables;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.erickogi14gmail.study20.Main.DB.DBOperations;
import com.erickogi14gmail.study20.Main.models.TimeTable_model;
import com.erickogi14gmail.study20.Main.utills.TouchyWebView;
import com.erickogi14gmail.study20.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReadTimeTable extends AppCompatActivity {
    TouchyWebView webView;
    DBOperations dbOperations = new DBOperations(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_time_table);
        webView = (TouchyWebView) findViewById(R.id.webView);
        Intent intent = getIntent();
        String code = intent.getStringExtra("TIMETABLE_ID");
        Log.d("codee", code);

        ArrayList<TimeTable_model> data = dbOperations.getTimeTablesListtById(code);
        String html = data.get(0).getTimetable_content();
        setWebView(html);
    }

    private void setWebView(String html) {
        webView.getSettings().setJavaScriptEnabled(true);


//    webView.getSettings().setUseWideViewPort(true);
//    webView.getSettings().setLoadWithOverviewMode(true);
//    webView.getSettings().setAllowFileAccess(true);
        // webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setWebViewClient(new MyWebViewClient(this));
        // webView.getSettings().setDefaultFontSize(25);
        webView.loadDataWithBaseURL(null, null, null, null, null);
        webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);
        //  webView.loadUrl(html);
        webView.setWebViewClient(new MyWebViewClient(this));

//        webView.setWebChromeClient(new WebChromeClient() {
//
//
//        });

    }

    //    private class MyWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//          //  if (url.contains("calendar")) {
//                try {
//                    URL aURL = new URL(url);
//
//                    System.out.println("protocol = " + aURL.getProtocol());
//                    System.out.println("authority = " + aURL.getAuthority());
//                    System.out.println("host = " + aURL.getHost());
//                    System.out.println("port = " + aURL.getPort());
//                    System.out.println("path = " + aURL.getPath());
//                    System.out.println("query = " + aURL.getQuery());
//                    System.out.println("filename = " + aURL.getFile());
//                    System.out.println("ref = " + aURL.getRef());
//                    onAddEventClicked(aURL.getPath(),aURL.getPath(),aURL.getQuery(),aURL.getQuery(),aURL.getRef());
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                return false;
//          //  } else {
//           //     view.loadUrl(url);
//           //     return true;
//            //}
//        }
//    }
    public void onAddEventClicked(String start, String end, String title, String desc, String loc)
            throws ParseException {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");


        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = formatter.parse(start);
        Date endDate = formatter.parse(end);

        long startTime = startDate.getTime();
        long endTime = endDate.getTime();


        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        Log.d("stringtotle1", title);
        intent.putExtra(CalendarContract.Events.TITLE, title);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, desc);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, loc);


        startActivity(intent);
    }
}
