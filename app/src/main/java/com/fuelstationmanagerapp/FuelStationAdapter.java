package com.fuelstationmanagerapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fuelstationmanagerapp.model.FuelStation;

public class FuelStationAdapter {
    private Context context;
    private FuelStation[] fuelStations;

    public FuelStationAdapter(Context context, FuelStation[] fuelStations) {
        this.context = context;
        this.fuelStations = fuelStations;
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
