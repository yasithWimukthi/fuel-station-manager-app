package com.fuelstationmanagerapp;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fuelstationmanagerapp.model.FuelStation;
import com.fuelstationmanagerapp.retrofit.Api;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  implements DrawerAdapter.OnItemSelectedListener{

    //add fuel station
//    private EditText inputOwner, inputName, inputLocation;
//    private Button btnAdd;

    private static final int POS_CLOSE = 0;
    private static final int POS_DASHBOARD = 1;
    private static final int POS_ADD_FUEL_STATION = 2;
    private static final int POS_LOGOUT = 3;
    private static final String TAG = "Main Activity";

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slideRootNav;
    private BottomNavigationView bottomNavBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        try{
            setSupportActionBar(toolbar);
        }catch(IllegalStateException e){
            Log.e(TAG, "onCreate: "+ e.getMessage() );
        }

//        //add fuel station
//        inputOwner = findViewById(R.id.inputOwner);
//        inputName = findViewById(R.id.inputName);
//        inputLocation = findViewById(R.id.inputLocation);
//        btnAdd = findViewById(R.id.btnAdd);

        slideRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();


        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_CLOSE),
                createItemFor(POS_DASHBOARD),
                createItemFor(POS_ADD_FUEL_STATION),
                new SpaceItem(150),
                createItemFor(POS_LOGOUT)
        ));

        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);

//        // adding on click listener to the button.
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // validating if the text field is empty or not.
//                if (inputOwner.getText().toString().isEmpty() && inputName.getText().toString().isEmpty()  && inputLocation.getText().toString().isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please enter all the values", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                // calling a method to post the data and passing variables.
//                postData(inputOwner.getText().toString(), inputName.getText().toString(), inputLocation.getText().toString());
//            }
//        });

    }

//    private void postData(String owner, String sName, String location) {
//
//        // below line is for displaying our progress bar.
////        loadingPB.setVisibility(View.VISIBLE);
//
//        // on below line creating a retrofit
//        // builder and passing our base url
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://localhost:4001/api/v1/fuelStations")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        Api retrofitAPI = retrofit.create(Api.class);
//        FuelStation modal = new FuelStation(owner, sName, location);
//
//        // calling a method to create a post and passing our modal class.
//        Call<FuelStation> call = retrofitAPI.createPost(modal);
//
//        // on below line we are executing our method.
//        call.enqueue(new Callback<FuelStation>() {
//            @Override
//            public void onResponse(Call<FuelStation> call, Response<FuelStation> response) {
//                // this method is called when we get response from our api.
//                Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
//
//                inputOwner.setText("");
//                inputName.setText("");
//                inputLocation.setText("");
//
//                FuelStation responseFromAPI = response.body();
//
////                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Job : " + responseFromAPI.getJob();
//
////                responseTV.setText(responseString);
//            }
//
//            @Override
//            public void onFailure(Call<FuelStation> call, Throwable t) {
//
////                responseTV.setText("Error found is : " + t.getMessage());
//            }
//        });
//    }

    private DrawerItem createItemFor(int position){
        return new SimpleItem(screenIcons[position],screenTitles[position])
                .withIconTint(color(R.color.colorPrimary))
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.colorPrimary))
                .withSelectedTextTint(color(R.color.colorPrimary));
    }

    @ColorInt
    private int color(@ColorRes int res){
        return ContextCompat.getColor(this,res);
    }


    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];

        for(int i=0 ; i<ta.length() ; i++) {
            int id = ta.getResourceId(i,0);
            if (id != 0){
                icons[i] = ContextCompat.getDrawable(this,id);
            }
        }

        ta.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (position == POS_DASHBOARD) {
            toolbar.setTitle("Dashboard");
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.container, homeFragment);
        }else if (position == POS_ADD_FUEL_STATION)  {
            toolbar.setTitle("Add Fuel Station");
            AddFuleStationFragment fuelStationFragment = new AddFuleStationFragment();
            transaction.replace(R.id.container, fuelStationFragment);
        }else{
            finish();
        }

        slideRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }
}