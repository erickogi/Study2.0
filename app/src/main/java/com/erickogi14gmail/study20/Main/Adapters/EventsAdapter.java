package com.erickogi14gmail.study20.Main.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erickogi14gmail.study20.Main.mPicasso.PicassoClient;
import com.erickogi14gmail.study20.Main.models.Course_model;
import com.erickogi14gmail.study20.Main.models.Events_model;
import com.erickogi14gmail.study20.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kimani kogi on 5/19/2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    Context context;
    private ArrayList<Events_model> modelList;

    public EventsAdapter(Context context, ArrayList<Events_model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    public static String getMonthName(int month) {
        switch (month + 1) {
            case 1:
                return "Jan";

            case 2:
                return "Feb";

            case 3:
                return "Mar";

            case 4:
                return "Apr";

            case 5:
                return "May";

            case 6:
                return "Jun";

            case 7:
                return "Jul";

            case 8:
                return "Aug";

            case 9:
                return "Sep";

            case 10:
                return "Oct";

            case 11:
                return "Nov";

            case 12:
                return "Dec";
        }
        return "";
    }

    @Override
    public EventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Events_model model = modelList.get(position);
        holder.textView_list_item_title.setText(model.getEvent_title());
        holder.textView_list_item_price.setText(model.getEvent_price());
        Date startDate = null;
        Date endDate = null;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            startDate = formatter.parse(model.getEvent_start());
            endDate = formatter.parse(model.getEvent_end());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        holder.textView_list_item_date.setText(day + " " + getMonthName(month) + " " + year);
        holder.textView_list_item_location.setText(model.getEvent_specific_location());

        PicassoClient.LoadImage(context, model.getEvent_image(),
                holder.pic, holder.textView_list_item_title);
        // holder.pic.setVisibility(View.INVISIBLE);

//        DBOperations dbOperations = new DBOperations(context);
//        if (dbOperations.getCourseById(String.valueOf(model.getASSIGNMENT_ID()))) {
//            holder.state.setImageResource(R.drawable.ic_check_circle_black_24dp);
//        } else {
//            holder.state.setImageResource(R.drawable.ic_get_app_black_24dp);
//        }


    }

    public void updateList(ArrayList<Events_model> list) {
        modelList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public interface OnItemClickListener {

        void onItemClick(Course_model item);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_list_item_title,
                textView_list_item_price, textView_list_item_date,
                textView_list_item_location;
        ImageView pic;

        public MyViewHolder(View itemView) {
            super(itemView);
            pic = (ImageView) itemView.findViewById(R.id.event_img);
            textView_list_item_title = (TextView) itemView.findViewById(R.id.event_title);
            textView_list_item_price = (TextView) itemView.findViewById(R.id.event_price);
            textView_list_item_location = (TextView) itemView.findViewById(R.id.event_location);
            textView_list_item_date = (TextView) itemView.findViewById(R.id.event_date);

        }
    }
}

