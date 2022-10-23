package com.fuelstationmanagerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> {

    public QueueAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.queue_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView statusImageView;
        TextView textViewName;
        TextView textViewDate;
        TextView textViewStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statusImageView = itemView.findViewById(R.id.statusImageView);
            textViewName = itemView.findViewById(R.id.textName);
            textViewDate = itemView.findViewById(R.id.textDate);
            textViewStatus = itemView.findViewById(R.id.textStatus);
        }
    }
}

