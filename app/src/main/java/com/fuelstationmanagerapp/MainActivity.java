package com.fuelstationmanagerapp;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private static final int POS_CLOSE = 0;
    private static final int POS_DASHBOARD = 1;
    private static final int POS_ADD_FUEL_STATION = 2;
    private static final int POS_FUEL_STATIONS = 3;
    private static final int POS_LOGOUT = 4;
    private static final String TAG = "Main Activity";

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slideRootNav;
    private BottomNavigationView bottomNavBar;
    Toolbar toolbar;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing username.
    public static final String NAME_KEY = "name_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);

        // initializing our shared preferences.
        sharedpreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        try{
            setSupportActionBar(toolbar);
        }catch(IllegalStateException e){
            Log.e(TAG, "onCreate: "+ e.getMessage() );
        }

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
                createItemFor(POS_FUEL_STATIONS),
                new SpaceItem(150),
                createItemFor(POS_LOGOUT)
        ));

        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
    }

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
        }else if (position == POS_ADD_FUEL_STATION) {
            toolbar.setTitle("Add Fuel Station");
            AddFuleStationFragment fuelStationFragment = new AddFuleStationFragment();
            transaction.replace(R.id.container, fuelStationFragment);
        }else if (position == POS_FUEL_STATIONS) {
            toolbar.setTitle("Fuel Stations");
            FuelStationsFragment fuelStationsFragment = new FuelStationsFragment();
            transaction.replace(R.id.container, fuelStationsFragment);
        }else{
            // calling method to edit values in shared prefs.
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.clear();
            editor.apply();

            Intent i = new Intent(MainActivity.this, LoginScreen.class);
            startActivity(i);
            finish();
        }

        slideRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void navigateToUpdateFuelStation(){
//        toolbar.setTitle("Update Fuel Station");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        UpdateFuelStatusFragment updateFuelStatusFragment = new UpdateFuelStatusFragment();
        transaction.replace(R.id.container, updateFuelStatusFragment);
        transaction.commit();
    }
}