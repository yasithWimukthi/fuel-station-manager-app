package com.fuelstationmanagerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fuelstationmanagerapp.model.FuelStation;

public class FuelStationAdapter extends RecyclerView.Adapter<FuelStationAdapter.ViewHolder>{
    private Context context;
    private FuelStation[] fuelStations;

    public FuelStationAdapter(Context context, FuelStation[] fuelStations) {
        this.context = context;
        this.fuelStations = fuelStations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fuel_station_list_item,parent,false);
        FuelStationAdapter.ViewHolder viewHolder = new FuelStationAdapter.ViewHolder(view);
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
        TextView textViewName;
        TextView textViewPetrolStatus;
        TextView textViewDieselStatus;
        TextView textViewGasolineStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textName);
            textViewPetrolStatus = itemView.findViewById(R.id.textPetrolStatus);
            textViewDieselStatus = itemView.findViewById(R.id.textDieselStatus);
            textViewGasolineStatus = itemView.findViewById(R.id.textGasolineStatus);
        }
    }
}
