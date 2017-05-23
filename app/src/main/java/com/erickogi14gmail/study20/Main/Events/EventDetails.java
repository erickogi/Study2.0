package com.erickogi14gmail.study20.Main.Events;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.erickogi14gmail.study20.Main.mPicasso.PicassoClient;
import com.erickogi14gmail.study20.Main.models.Events_model;
import com.erickogi14gmail.study20.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventDetails extends AppCompatActivity {
    ArrayList<Events_model> data = new ArrayList<>();
    ImageView imageView, imageViewCalendar, imageViewMaps, imageViewTicket;
    TextView textViewTitle, textViewBy, textViewStart, textViewEnd, textViewGl, textViewSl,
            textViewPrice, textViewDescription, textViewOrganizer;
    Button buttonSave, buttonShare;


    Events_model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView = (ImageView) findViewById(R.id.event_img);
        textViewTitle = (TextView) findViewById(R.id.event_titlef);
        textViewBy = (TextView) findViewById(R.id.event_byf);
        textViewEnd = (TextView) findViewById(R.id.event_date_end);
        textViewStart = (TextView) findViewById(R.id.event_date_start);
        textViewGl = (TextView) findViewById(R.id.event_general_location);
        textViewSl = (TextView) findViewById(R.id.event_specific_location);
        textViewPrice = (TextView) findViewById(R.id.event_ticket_price);
        textViewDescription = (TextView) findViewById(R.id.event_description);
        textViewOrganizer = (TextView) findViewById(R.id.event_organizer);

        imageViewCalendar = (ImageView) findViewById(R.id.event_calendar);
        imageViewMaps = (ImageView) findViewById(R.id.event_location);
        imageViewTicket = (ImageView) findViewById(R.id.event_ticket);

        buttonSave = (Button) findViewById(R.id.event_save);
        buttonShare = (Button) findViewById(R.id.event_share);

        imageViewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onAddEventClicked();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        imageViewMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps();

            }
        });
        imageViewTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onAddEventClicked();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        Intent intent = getIntent();
        int position = intent.getIntExtra("dataposition", 0);
        data = (ArrayList<Events_model>) intent.getSerializableExtra("data");
        model = data.get(position);

        PicassoClient.LoadImage(getApplication(), model.getEvent_image(),
                imageView, textViewTitle);

        textViewTitle.setText(model.getEvent_title());
        textViewBy.setText(model.getEvent_by());
        textViewStart.setText(model.getEvent_start());
        textViewEnd.setText(model.getEvent_end());
        textViewGl.setText(model.getEvent_general_location());
        textViewSl.setText(model.getEvent_specific_location());
        textViewPrice.setText(model.getEvent_price());
        textViewDescription.setText(model.getEvent_description());
        textViewOrganizer.setText(model.getEvent_by());
        getSupportActionBar().setTitle(model.getEvent_title());


    }

    private void openMaps() {

        model.getEvent_lat();
        model.getEvent_long();

        String strUri = "http://maps.google.com/maps?q=loc:" + model.getEvent_lat() + "," + model.getEvent_long() + " (" + "Event Location" + ")";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

        startActivity(intent);
    }

    public void onAddEventClicked() throws ParseException {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");


        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = formatter.parse(model.getEvent_start());
        Date endDate = formatter.parse(model.getEvent_end());

        long startTime = startDate.getTime();
        long endTime = endDate.getTime();


        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

        intent.putExtra(CalendarContract.Events.TITLE, textViewTitle.getText());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, textViewDescription.getText());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, textViewGl.getText() + " " + textViewSl.getText());


        startActivity(intent);
    }
}
