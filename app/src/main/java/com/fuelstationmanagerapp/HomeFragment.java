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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fuelstationmanagerapp.dbModel.Customer;
import com.fuelstationmanagerapp.dbModel.FuelQueue;
import com.fuelstationmanagerapp.dbModel.FuelStation;
import com.fuelstationmanagerapp.model.QueueItem;
import com.fuelstationmanagerapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String queueTypes[] = {"Motorcycle","Car","Three Wheeler"};
    private String fuelStations[] = {"Petrol Station","Diesel Station","Kerosene Station"};
    private String fuelTypes[] = {"Petrol","Diesel","Gasoline"};
    List<FuelStation> fuelStationList = new ArrayList<>();
    List<Customer> customerList = new ArrayList<>();

    FuelQueue fuelQueueObj;

    private AutoCompleteTextView queueTypeAutoCompleteTextView;
    private AutoCompleteTextView fuelStationAutoCompleteTextView;
    private AutoCompleteTextView fuelTypeAutoCompleteTextView;
    private RecyclerView queueRecyclerView;
    private ArrayAdapter<String> queueTypeAdapter;
    private ArrayAdapter<String> fuelStationAdapter;
    private ArrayAdapter<String> fuelTypeAdapter;
    private String queueType;
    private String fuelStation;
    private String fuelType;

    //display fuel queue
    private TextView fuelStationNameView, fuelTypeView, vehicleTypeView, fuelStatusView, vehicleCountView;
    private Button viewQueueBtn;

    public HomeFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        queueRecyclerView = v.findViewById(R.id.queueRecyclerView);
        queueTypeAutoCompleteTextView = (AutoCompleteTextView) getView().findViewById(R.id.queueTypeInput);
        fuelTypeAutoCompleteTextView = (AutoCompleteTextView) getView().findViewById(R.id.fuelTypeInput);
        queueTypeAdapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_list_item, queueTypes);
        fuelTypeAdapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_list_item, fuelTypes);
        queueTypeAutoCompleteTextView.setAdapter(queueTypeAdapter);
        fuelTypeAutoCompleteTextView.setAdapter(fuelTypeAdapter);

        //display fuel queue
        fuelStationNameView = getView().findViewById(R.id.fuelStationName);
        fuelTypeView = getView().findViewById(R.id.fuelType);
        vehicleTypeView = getView().findViewById(R.id.vehicleType);
        fuelStatusView = getView().findViewById(R.id.fuelStatus);
        vehicleCountView = getView().findViewById(R.id.vehicleCount);
        viewQueueBtn = getView().findViewById(R.id.viewQueueBtn);

        // adding on click listener to the button.
        viewQueueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFuelQueues(fuelStation, queueType, fuelType);

            }
        });

        /**
         * This is the listener for the queue type auto complete text view
         */
        queueTypeAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            queueType = parent.getItemAtPosition(position).toString();
        });

        fuelStationAutoCompleteTextView = (AutoCompleteTextView) getView().findViewById(R.id.fuelStationTypeInput);
        getFuelStations();

        /**
         * This is the listener for the fuel station auto complete text view
         */
        fuelStationAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            fuelStation = parent.getItemAtPosition(position).toString();
        });

        /**
         * This is the listener for the fuel type auto complete text view
         */
        fuelTypeAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            fuelType = parent.getItemAtPosition(position).toString();
        });

        /**
         * setting queue recycler view
         */
        queueRecyclerView.setHasFixedSize(true);
        queueRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        QueueItem[] queueItems = new QueueItem[]{
//                new QueueItem("name1","status1","time1"),
//                new QueueItem("name2","status2","time2"),
//                new QueueItem("name3","status3","time3"),
//                new QueueItem("name4","status4","time4")
//        };

//        QueueAdapter queueAdapter = new QueueAdapter(getContext(),queueItems);
//        queueRecyclerView.setAdapter(queueAdapter);

    }

    //API
    private void getFuelStations() {
        Call<List<FuelStation>> call = RetrofitClient.getInstance().getMyApi().getFuelStations();
        call.enqueue(new Callback<List<FuelStation>>() {
            @Override
            public void onResponse(Call<List<FuelStation>> call, Response<List<FuelStation>> response) {
                fuelStationList = response.body();
                String[] fuelStationsNameArray = new String[fuelStationList.size()];

                for (int i = 0; i < fuelStationList.size(); i++) {
                    fuelStationsNameArray[i] = fuelStationList.get(i).getName();
                }
//                System.out.println("hi hashen.............."+response.body());
                fuelStationAdapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_list_item, fuelStationsNameArray);
                fuelStationAutoCompleteTextView.setAdapter(fuelStationAdapter);
            }

            @Override
            public void onFailure(Call<List<FuelStation>> call, Throwable t) {
                System.out.println("error........."+t.getMessage());
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }

    //API
    private void getFuelQueues(String stationName, String vehicleType, String fuelType) {
        System.out.println(stationName+"  "+vehicleType+"  "+fuelType);
        Call<FuelQueue> call = RetrofitClient.getInstance().getMyApi().getFuelQueues(stationName, vehicleType, fuelType);
        call.enqueue(new Callback<FuelQueue>() {
            @Override
            public void onResponse(Call<FuelQueue> call, Response<FuelQueue> response) {
                fuelQueueObj = response.body();
                System.out.println("success............"+fuelQueueObj.getCustomers().get(0).getCustomerName());
                fuelStationNameView.append(": "+fuelQueueObj.getFuelStationName());
                fuelTypeView.append(": "+fuelQueueObj.getFuelType());
                vehicleTypeView.append(": "+fuelQueueObj.getVehicleType());
                fuelStatusView.append(": "+fuelQueueObj.getFuelStatus());
                vehicleCountView.append(": "+fuelQueueObj.getCount());
                customerList = fuelQueueObj.getCustomers();

                //display customer list in card view
                Customer[] customersArray = new Customer[customerList.size()];

                for (int i = 0; i < customerList.size(); i++) {
                    customersArray[i] = customerList.get(i);
                }

                QueueAdapter queueAdapter = new QueueAdapter(getContext(),customersArray);
                queueRecyclerView.setAdapter(queueAdapter);

            }

            @Override
            public void onFailure(Call<FuelQueue> call, Throwable t) {
                System.out.println("error........."+t.getMessage());
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }
}