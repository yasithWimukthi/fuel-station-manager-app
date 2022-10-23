package com.fuelstationmanagerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fuelstationmanagerapp.model.FuelStation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FuelStationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FuelStationsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView fuelStationsRecyclerView;


    public FuelStationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FuelStationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FuelStationsFragment newInstance(String param1, String param2) {
        FuelStationsFragment fragment = new FuelStationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fuel_stations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        fuelStationsRecyclerView = v.findViewById(R.id.fuelStationsRecyclerView);

        /**
         * setting fuel stations recycler view
         */

        fuelStationsRecyclerView.setHasFixedSize(true);
        fuelStationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FuelStation[] fuelStations = new FuelStation[]{
                new FuelStation("station 1", true, false, false),
                new FuelStation("station 2", false, true, false),
                new FuelStation("station 3", false, false, true),
                new FuelStation("station 4", true, false, false),
        };

        FuelStationAdapter fuelStationsAdapter = new FuelStationAdapter(getContext(),fuelStations);
        fuelStationsRecyclerView.setAdapter(fuelStationsAdapter);
    }
}