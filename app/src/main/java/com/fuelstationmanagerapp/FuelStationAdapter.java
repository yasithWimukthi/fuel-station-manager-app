package com.fuelstationmanagerapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.fuelstationmanagerapp.dbModel.StationStatus;
import com.fuelstationmanagerapp.model.FuelStation;

public class FuelStationAdapter extends RecyclerView.Adapter<FuelStationAdapter.ViewHolder>{
    private Context context;
    private StationStatus[] fuelStations;
    public static String _id;

    public FuelStationAdapter(Context context, StationStatus[] fuelStations) {
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
        final StationStatus fuelStation = fuelStations[position];
        holder.textViewName.setText(fuelStation.getName());
        holder.textViewPetrolStatus.setText("Petrol: "+fuelStation.getPetrolStatus());
        holder.textViewDieselStatus.setText("Diesel: "+fuelStation.getDieselStatus());
        holder.textViewGasolineStatus.setText("Gasoline: "+fuelStation.getGasolineStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _id = fuelStation.get_id();
                Toolbar toolbar = ((MainActivity)context).findViewById(R.id.toolbar);
                toolbar.setTitle("Update Fuel Station");
                FragmentTransaction transaction = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                UpdateFuelStatusFragment updateFuelStatusFragment = new UpdateFuelStatusFragment();
                transaction.replace(R.id.container, updateFuelStatusFragment);
//                Bundle bundle = new Bundle();
//              bundle.putSerializable("fuelStation", fuelStation);
//                getParentFragmentManager().setFragmentResult("fuelStation", bundle);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return fuelStations.length;
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
