package com.fuelstationmanagerapp.retrofit;

import com.fuelstationmanagerapp.dbModel.FuelStation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    public static String BASE_URL = "http://192.168.1.110:4001/api/v1/";

    //API method to create fuel station
    @POST("fuelStations")
    Call<FuelStation> createFuelStation(@Body FuelStation dataModal);

    //API method to get all fuel stations
    @GET("fuelStations")
    Call<List<FuelStation>> getFuelStations();
}
