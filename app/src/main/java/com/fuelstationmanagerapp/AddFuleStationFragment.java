package com.fuelstationmanagerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fuelstationmanagerapp.dbModel.FuelStation;
import com.fuelstationmanagerapp.retrofit.Api;
import com.fuelstationmanagerapp.retrofit.RetrofitClient;
import com.sdsmdg.tastytoast.TastyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFuleStationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class AddFuleStationFragment extends Fragment {

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing username.
    public static final String NAME_KEY = "name_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String email;
    String name;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //add fuel station
    private EditText inputOwner, inputName, inputLocation;
    private Button btnAdd;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFuleStationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFuleStationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFuleStationFragment newInstance(String param1, String param2) {
        AddFuleStationFragment fragment = new AddFuleStationFragment();
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
        name = sharedpreferences.getString(NAME_KEY, null);
    }

    private void postData(String owner, String sName, String location) {
        System.out.println("came in to post Data");

        // calling a method to create a post and passing our modal class.
        FuelStation modal = new FuelStation(owner, sName, location);
        Call<FuelStation> call = RetrofitClient.getInstance().getMyApi().createFuelStation(modal);

        call.enqueue(new Callback<FuelStation>() {
            @Override
            public void onResponse(Call<FuelStation> call, Response<FuelStation> response) {
                inputOwner.setText("");
                inputName.setText("");
                inputLocation.setText("");
                FuelStation responseFromAPI = response.body();
                TastyToast.makeText(getContext(), "Fuel Station Was Saved Successfully.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            }

            @Override
            public void onFailure(Call<FuelStation> call, Throwable t) {
                System.out.println(t.getMessage());
                TastyToast.makeText(getContext(), "Fuel Station Was Not Saved.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //add fuel station
        inputOwner = getView().findViewById(R.id.inputOwner);
        inputName = getView().findViewById(R.id.inputName);
        inputLocation = getView().findViewById(R.id.inputLocation);
        btnAdd = getView().findViewById(R.id.btnAdd);

        //adding input owner from session
        inputOwner.setText(name);
        inputOwner.setEnabled(false);


        // adding on click listener to the button.
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (inputOwner.getText().toString().isEmpty() && inputName.getText().toString().isEmpty() && inputLocation.getText().toString().isEmpty()) {
                    return;
                }
                // calling a method to post the data and passing variables.
                postData(inputOwner.getText().toString(), inputName.getText().toString(), inputLocation.getText().toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_fule_station, container, false);
    }
}