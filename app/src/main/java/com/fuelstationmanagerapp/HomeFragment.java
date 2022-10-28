package com.fuelstationmanagerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fuelstationmanagerapp.dbModel.Customer;
import com.fuelstationmanagerapp.dbModel.FuelQueue;
import com.fuelstationmanagerapp.dbModel.FuelStation;
import com.fuelstationmanagerapp.dbModel.NameObj;
import com.fuelstationmanagerapp.dbModel.SingleQueueObject;
import com.fuelstationmanagerapp.model.QueueItem;
import com.fuelstationmanagerapp.retrofit.RetrofitClient;
import com.sdsmdg.tastytoast.TastyToast;

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

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing username.
    public static final String NAME_KEY = "name_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String email;

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
    private CardView statusCardView;
    private ImageView emptyImage;
    private String queueType;
    private String fuelStation;
    private String fuelType;


    //display fuel queue
    private TextView fuelStationNameView, fuelTypeView, vehicleTypeView, fuelStatusView, vehicleCountView;
    private Button viewQueueBtn;

    //Initialising join and update queue buttons
    private Button joinQueue, exitBefore, exitAfter;


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
        // initializing our shared preferences.
        sharedpreferences = getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // storing it in our string variable.
        email = sharedpreferences.getString(EMAIL_KEY, null);
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
        statusCardView = (CardView) getView().findViewById(R.id.statusCardView);
        emptyImage = (ImageView) getView().findViewById(R.id.emptyImage);
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
                emptyImage.setVisibility(View.GONE);
                statusCardView.setVisibility(View.VISIBLE);
                queueRecyclerView.setVisibility(View.VISIBLE);
                joinQueue.setVisibility(View.VISIBLE);
                exitBefore.setVisibility(View.VISIBLE);
                exitAfter.setVisibility(View.VISIBLE);
            }
        });
        //find buttons join and update queue buttons by id
        joinQueue = getView().findViewById(R.id.joinToQueueBtn);
        exitBefore = getView().findViewById(R.id.beforePumpBtn);
        exitAfter = getView().findViewById(R.id.afterPumpBtn);

        //adding on click listners for join and update queue buttons
        joinQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fuelQueueObj!=null){
                    SingleQueueObject singleQueueObject = initSingleQueueObject();
                    singleQueueObject.setStatus("in");
                    joinFuelQueue(singleQueueObject);
                }
            }
        });

        exitBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked");
                if(fuelQueueObj!=null){
                    exitBeforePump(sharedpreferences.getString(NAME_KEY, null));
                }
            }
        });

        exitAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked");
                if(fuelQueueObj!=null){
                    exitAfterPump(sharedpreferences.getString(NAME_KEY, null));
                }
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
                fuelStationAdapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_list_item, fuelStationsNameArray);
                fuelStationAutoCompleteTextView.setAdapter(fuelStationAdapter);
            }

            @Override
            public void onFailure(Call<List<FuelStation>> call, Throwable t) {
                System.out.println("error........."+t.getMessage());
            }

        });
    }

    //API
    private void getFuelQueues(String stationName, String vehicleType, String fuelType) {
        Call<FuelQueue> call = RetrofitClient.getInstance().getMyApi().getFuelQueues(stationName, vehicleType, fuelType);
        call.enqueue(new Callback<FuelQueue>() {
            @Override
            public void onResponse(Call<FuelQueue> call, Response<FuelQueue> response) {
                fuelQueueObj = response.body();

                //added if condition to avoid multiple appends

                fuelStationNameView.setText("");
                fuelTypeView.setText("");
                vehicleTypeView.setText("");
                fuelStatusView.setText("");
                vehicleCountView.setText("");

                fuelStationNameView.append("Station Name: "+fuelQueueObj.getFuelStationName());
                fuelTypeView.append("Fuel Type: "+fuelQueueObj.getFuelType());
                vehicleTypeView.append("Vehicle Type: "+fuelQueueObj.getVehicleType());
                fuelStatusView.append("Fuel Status: "+fuelQueueObj.getFuelStatus());
                vehicleCountView.append("Num of Vehicles: "+fuelQueueObj.getCount());

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
            }

        });
    }

    //Initialise singleQueueObject
    private SingleQueueObject initSingleQueueObject(){
        SingleQueueObject singleQueueObject = new SingleQueueObject();
        singleQueueObject.setFuelStation(fuelQueueObj.getFuelStationId());
        singleQueueObject.setFuelTypeName(fuelType);
        singleQueueObject.setCustomerName(sharedpreferences.getString(NAME_KEY, null));
        singleQueueObject.setVehicleType(queueType);

        return singleQueueObject;
    }
    //API
    private void joinFuelQueue(SingleQueueObject singleQueueObject) {
        Call<SingleQueueObject> call = RetrofitClient.getInstance().getMyApi().joinTheQueue(singleQueueObject);
        call.enqueue(new Callback<SingleQueueObject>() {
            @Override
            public void onResponse(Call<SingleQueueObject> call, Response<SingleQueueObject> response) {

                SingleQueueObject obj = new SingleQueueObject();
                obj = response.body();
                if(obj.get_id().equals("0")){
                    TastyToast.makeText(getContext(), "You are already in the petrol queue", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }
                TastyToast.makeText(getContext(), "Joined the queue", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                getFuelQueues(fuelStation, queueType, fuelType);
            }

            @Override
            public void onFailure(Call<SingleQueueObject> call, Throwable t) {
                TastyToast.makeText(getContext(), "An error has occurred", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }
        });
    }

    //API
    private void exitBeforePump(String name) {
        NameObj nameObj = new NameObj(name);
        Call<SingleQueueObject> call = RetrofitClient.getInstance().getMyApi().exitBeforePump(nameObj);
        call.enqueue(new Callback<SingleQueueObject>() {
            @Override
            public void onResponse(Call<SingleQueueObject> call, Response<SingleQueueObject> response) {
                TastyToast.makeText(getContext(), "Exited the queue", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                getFuelQueues(fuelStation, queueType, fuelType);
            }

            @Override
            public void onFailure(Call<SingleQueueObject> call, Throwable t) {
                TastyToast.makeText(getContext(), "An error has occurred", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }

        });
    }

    //API
    private void exitAfterPump(String name) {
        NameObj nameObj = new NameObj(name);
        Call<SingleQueueObject> call = RetrofitClient.getInstance().getMyApi().exitAfterPump(nameObj);
        call.enqueue(new Callback<SingleQueueObject>() {
            @Override
            public void onResponse(Call<SingleQueueObject> call, Response<SingleQueueObject> response) {
                TastyToast.makeText(getContext(), "Exited the queue", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                getFuelQueues(fuelStation, queueType, fuelType);
            }

            @Override
            public void onFailure(Call<SingleQueueObject> call, Throwable t) {
                TastyToast.makeText(getContext(), "An error has occurred", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }

        });
    }

}