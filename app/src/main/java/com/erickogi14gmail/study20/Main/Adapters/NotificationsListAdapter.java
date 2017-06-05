package com.erickogi14gmail.study20.Main.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.erickogi14gmail.study20.Main.models.Notifications_model;
import com.erickogi14gmail.study20.R;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/28/2017.
 */

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.MyViewHolder> {

    Context context;
    private ArrayList<Notifications_model> modelList;
    //private int a;

    public NotificationsListAdapter(Context context, ArrayList<Notifications_model> modelList) {
        this.context = context;
        this.modelList = modelList;

    }


    @Override
    public NotificationsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationsListAdapter.MyViewHolder holder, int position) {
        Notifications_model model = modelList.get(position);
        holder.textView_list_item_name.setText(model.getNotifications_title());
        holder.textView_list_item_description.setText(model.getNotifications_description());
        //  DBOperations dbOperations=new DBOperations(context);
        // int no= dbOperations.getNoOfNotificationsUnreadByChannel(0,model.getNotification_c_name());
        holder.textView_list_item_date.setText(model.getNotification_date());


    }

    public void updateList(ArrayList<Notifications_model> list) {
        modelList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public interface OnItemClickListener {

        void onItemClick(Notifications_model item);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_list_item_name,
                textView_list_item_description,
                textView_list_item_date;
        Button state;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView_list_item_name = (TextView) itemView.findViewById(R.id.list_item_name);
            textView_list_item_description = (TextView) itemView.findViewById(R.id.list_item_description);
            textView_list_item_date = (TextView) itemView.findViewById(R.id.list_item_date);

        }
    }
}


