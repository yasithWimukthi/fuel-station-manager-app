package com.fuelstationmanagerapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.fuelstationmanagerapp.dbModel.StationStatus;
import com.fuelstationmanagerapp.dbModel.StationStatus;
import com.fuelstationmanagerapp.retrofit.RetrofitClient;
import com.sdsmdg.tastytoast.TastyToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateFuelStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateFuelStatusFragment extends Fragment {

    private String id = "6357146b7078e001c7c5f1ed";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String status[] = {"Available", "Not Available"};

    private AutoCompleteTextView petrolStatusAutoCompleteTextView;
    private AutoCompleteTextView dieselStatusAutoCompleteTextView;
    private AutoCompleteTextView gasolineStatusAutoCompleteTextView;
    private ArrayAdapter<String> petrolStatusAdapter;
    private ArrayAdapter<String> dieselStatusAdapter;
    private ArrayAdapter<String> gasolineStatusAdapter;
    private EditText inputPetrolArrivalTimeEditText;
    private EditText inputPetrolFinishTimeEditText;
    private EditText inputDieselArrivalTimeEditText;
    private EditText inputDieselFinishTimeEditText;
    private EditText inputGasolineArrivalTimeEditText;
    private EditText inputGasolineFinishTimeEditText;
    private Button buttonUpdate;

    private String petrolStatus;
    private String dieselStatus;
    private String gasolineStatus;


    public UpdateFuelStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateFuelStatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateFuelStatusFragment newInstance(String param1, String param2) {
        UpdateFuelStatusFragment fragment = new UpdateFuelStatusFragment();
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
        return inflater.inflate(R.layout.fragment_update_fuel_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        petrolStatusAutoCompleteTextView = v.findViewById(R.id.petrolStatusInput);
        petrolStatusAdapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_list_item, status);
        petrolStatusAutoCompleteTextView.setAdapter(petrolStatusAdapter);
        dieselStatusAutoCompleteTextView = v.findViewById(R.id.dieselStatusInput);
        dieselStatusAdapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_list_item, status);
        dieselStatusAutoCompleteTextView.setAdapter(dieselStatusAdapter);
        gasolineStatusAutoCompleteTextView = v.findViewById(R.id.gasolineStatusInput);
        gasolineStatusAdapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_list_item, status);
        gasolineStatusAutoCompleteTextView.setAdapter(gasolineStatusAdapter);

        inputPetrolArrivalTimeEditText = v.findViewById(R.id.inputPetrolArrivalTime);
        inputPetrolFinishTimeEditText = v.findViewById(R.id.inputPetrolFinishedTime);
        inputDieselArrivalTimeEditText = v.findViewById(R.id.inputDieselArrivalTime);
        inputDieselFinishTimeEditText = v.findViewById(R.id.inputDieselFinishedTime);
        inputGasolineArrivalTimeEditText = v.findViewById(R.id.inputGasolineArrivalTime);
        inputGasolineFinishTimeEditText = v.findViewById(R.id.inputGasolineFinishedTime);
        buttonUpdate = v.findViewById(R.id.btnUpdate);

        /**
         *  This is the listener for the petrol status auto complete text view
         */

        petrolStatusAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            petrolStatus = parent.getItemAtPosition(position).toString();
        });

        /**
         * This is the listener for the diesel status auto complete text view
         */
        dieselStatusAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            dieselStatus = parent.getItemAtPosition(position).toString();
        });

        /**
         * This is the listener for the gasoline status auto complete text view
         */
        gasolineStatusAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            gasolineStatus = parent.getItemAtPosition(position).toString();
        });

        /**
         * This is the listener for the petrol arrival time edit text
         */
        inputPetrolArrivalTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(inputPetrolArrivalTimeEditText);
            }
        });

        /**
         * This is the listener for the petrol finish time edit text
         */
        inputPetrolFinishTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(inputPetrolFinishTimeEditText);
            }
        });

        /**
         * This is the listener for the diesel arrival time edit text
         */
        inputDieselArrivalTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(inputDieselArrivalTimeEditText);
            }
        });

        /**
         * This is the listener for the diesel finish time edit text
         */
        inputDieselFinishTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(inputDieselFinishTimeEditText);
            }
        });

        /**
         * This is the listener for the gasoline arrival time edit text
         */
        inputGasolineArrivalTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(inputGasolineArrivalTimeEditText);
            }
        });

        /**
         * This is the listener for the gasoline finish time edit text
         */
        inputGasolineFinishTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(inputGasolineFinishTimeEditText);
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFuelStatus();
            }
        });


    }

    /**
     * This method is used to show the date and time picker dialog
     *
     * @param date_time_in
     */
    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");

                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                };

                new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };

        new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    public StationStatus createStatusObject(){
        StationStatus stationStatus = new StationStatus();

        stationStatus.setPetrolStatus(petrolStatus);
        stationStatus.setDieselStatus(dieselStatus);
        stationStatus.setGasolineStatus(gasolineStatus);

        stationStatus.setPetrolArrivalTime(inputPetrolArrivalTimeEditText.getText().toString());
        stationStatus.setDieselArrivalTime(inputDieselArrivalTimeEditText.getText().toString());
        stationStatus.setGasolineArrivalTime(inputGasolineArrivalTimeEditText.getText().toString());

        stationStatus.setPetrolFinishedTime(inputPetrolFinishTimeEditText.getText().toString());
        stationStatus.setDieselFinishedTime(inputDieselFinishTimeEditText.getText().toString());
        stationStatus.setGasolineFinishedTime(inputGasolineFinishTimeEditText.getText().toString());

        return stationStatus;
    }

    //API call
    public void setFuelStatus(){
        StationStatus stationStatus = createStatusObject();

        System.out.println(stationStatus.getPetrolStatus());
        System.out.println(stationStatus.getPetrolArrivalTime());
        System.out.println(stationStatus.getPetrolFinishedTime());
        System.out.println(stationStatus.getDieselStatus());


        Call<StationStatus> call = RetrofitClient.getInstance().getMyApi().setFuelStatus(id, stationStatus);
        call.enqueue(new Callback<StationStatus>() {
            @Override
            public void onResponse(Call<StationStatus> call, Response<StationStatus> response) {
                System.out.println("update success");
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                TastyToast.makeText(getContext(), "Update Success", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            }

            @Override
            public void onFailure(Call<StationStatus> call, Throwable t) {
                System.out.println("update failed");
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                TastyToast.makeText(getContext(), "Update Failed", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }

        });
    }

}