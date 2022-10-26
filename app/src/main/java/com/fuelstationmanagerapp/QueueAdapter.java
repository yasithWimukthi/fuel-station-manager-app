package com.fuelstationmanagerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fuelstationmanagerapp.dbModel.Customer;
import com.fuelstationmanagerapp.model.QueueItem;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> {

    Context context;
    Customer[] queueItems;

    public QueueAdapter() {

    }

    public QueueAdapter(Context context, Customer[] queueItems) {
        this.context = context;
        this.queueItems = queueItems;
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
        final Customer queueItem = queueItems[position];
        holder.textViewName.setText(queueItem.getCustomerName());
        holder.textViewDate.setText(queueItem.getArrivalTime());
        holder.textViewDate.setText(queueItem.getDepartTime());
//        holder.statusImageView.setImageResource(queueItem.getMovieImage());
        holder.textViewStatus.setText(queueItem.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, queueItem.getCustomerName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return queueItems.length;
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

