package com.erickogi14gmail.study20.Main.TimeTables;

/**
 * Created by kimani kogi on 5/30/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyWebViewClient extends WebViewClient {
    private Context context;

    public MyWebViewClient(Context context) {
        this.context = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!url.isEmpty()) {
            try {
                URL aURL = new URL(url);

                System.out.println("protocol = " + aURL.getProtocol());
                System.out.println("authority = " + aURL.getAuthority());
                System.out.println("host = " + aURL.getHost());
                System.out.println("port = " + aURL.getPort());
                System.out.println("path = " + aURL.getPath());
                System.out.println("query = " + aURL.getQuery());
                System.out.println("filename = " + aURL.getFile());
                System.out.println("ref = " + aURL.getRef());
                onAddEventClicked(aURL.getPath(), aURL.getPath(), aURL.getQuery(), aURL.getQuery(), aURL.getRef());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    public void onAddEventClicked(String start, String end, String title, String desc, String loc)
            throws ParseException {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");


        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = formatter.parse(start.substring(1));
        Date endDate = formatter.parse(end.substring(1));

        long startTime = startDate.getTime();
        long endTime = endDate.getTime();


        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        Log.d("stringtotle1", title);
        intent.putExtra(CalendarContract.Events.TITLE, title.replace("%20", " ").replace("title", " ").replace("=", " "));
        intent.putExtra(CalendarContract.Events.DESCRIPTION, desc);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, loc);


        context.startActivity(intent);
    }
}