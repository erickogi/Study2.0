package com.erickogi14gmail.study20.Main.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.erickogi14gmail.study20.Main.DB.DBOperations;
import com.erickogi14gmail.study20.Main.models.Course_model;
import com.erickogi14gmail.study20.Main.models.Revision_model;
import com.erickogi14gmail.study20.R;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/22/2017.
 */

public class RevisionAdapter extends RecyclerView.Adapter<RevisionAdapter.MyViewHolder> {

    Context context;
    int a;
    private ArrayList<Revision_model> modelList;

    public RevisionAdapter(Context context, ArrayList<Revision_model> modelList, int a) {
        this.context = context;
        this.modelList = modelList;
        this.a = a;
    }


    @Override
    public RevisionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (a == 0) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.revision_list_item, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.revision_list_item_downloaded, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RevisionAdapter.MyViewHolder holder, int position) {
        Revision_model model = modelList.get(position);
        holder.textView_list_item_title.setText(model.getRevision_course_name());
        holder.textView_list_item_name.setText(model.getRevision_course_code());
        holder.textView_list_item_date.setText(model.getRevision_date());
        holder.textView_list_item_by.setText("By : " + model.getRevision_uploaded_by());

        if (a == 0) {
            DBOperations dbOperations = new DBOperations(context);
            if (dbOperations.getRevisionById(String.valueOf(model.getId()))) {
                //holder.state.setImageResource(R.drawable.ic_check_circle_black_24dp);
                holder.state.setText("Saved");
            } else {
                //  holder.state.setImageResource(R.drawable.ic_get_app_black_24dp);
                holder.state.setText("Download");
            }
        }


    }

    public void updateList(ArrayList<Revision_model> list) {
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
                textView_list_item_name, textView_list_item_date,
                textView_list_item_by;
        Button state;

        public MyViewHolder(View itemView) {
            super(itemView);
            if (a == 0) {
                state = (Button) itemView.findViewById(R.id.btn_status);
            }
            textView_list_item_title = (TextView) itemView.findViewById(R.id.list_item_title);
            textView_list_item_name = (TextView) itemView.findViewById(R.id.list_item_course_name);
            textView_list_item_by = (TextView) itemView.findViewById(R.id.list_item_by);
            textView_list_item_date = (TextView) itemView.findViewById(R.id.list_item_date);

        }
    }

}
