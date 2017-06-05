package com.erickogi14gmail.study20.Main.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erickogi14gmail.study20.Main.models.Files_model;
import com.erickogi14gmail.study20.R;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/31/2017.
 */

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.MyViewHolder> {

    Context context;
    int a;
    private ArrayList<Files_model> modelList;

    public FilesAdapter(Context context, ArrayList<Files_model> modelList) {
        this.context = context;
        this.modelList = modelList;

    }


    @Override
    public FilesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.files, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FilesAdapter.MyViewHolder holder, int position) {
        Files_model model = modelList.get(position);
        holder.textView_list_item_title.setText(model.getPost_title());

        holder.textView_list_item_date.setText(model.getPost_date());
        holder.textView_list_item_by.setText("By : " + model.getPost_author());


    }

    public void updateList(ArrayList<Files_model> list) {
        modelList = list;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public interface OnItemClickListener {

        void onItemClick(Files_model item);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_list_item_title,
                textView_list_item_date,
                textView_list_item_by;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView_list_item_title = (TextView) itemView.findViewById(R.id.list_item_title);

            textView_list_item_by = (TextView) itemView.findViewById(R.id.list_item_by);
            textView_list_item_date = (TextView) itemView.findViewById(R.id.list_item_date);

        }
    }

}

