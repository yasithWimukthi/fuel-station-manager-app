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

        String status = null;
        if(queueItem.getStatus().equals("in"))
            status = "In Queue";
        if(queueItem.getStatus().equals("pumped"))
            status = "Pumped Fuel";
        if(queueItem.getStatus().equals("notPumped"))
            status = "Did not pump Fuel";

        holder.textViewDate.setText("Arrived at: "+ queueItem.getArrivalTime());

        if(status!=null){
            holder.textViewStatus.setText(status);
        }

        if(queueItem.getDepartTime()!=null){
            holder.textViewDepartTime.setText("Left at: "+ queueItem.getDepartTime());
        }else {
            holder.textViewDepartTime.setText("");
        }

        if(!queueItem.getStatus().equals("in")){
            holder.textViewName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_circle_red, 0, 0, 0);
        }

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
        TextView textViewDepartTime;
        TextView textViewStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statusImageView = itemView.findViewById(R.id.statusImageView);
            textViewName = itemView.findViewById(R.id.textName);
            textViewDate = itemView.findViewById(R.id.textDate);
            textViewStatus = itemView.findViewById(R.id.textStatus);
            textViewDepartTime = itemView.findViewById(R.id.departTime);
        }
    }
}

