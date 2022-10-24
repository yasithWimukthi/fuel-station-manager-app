package com.fuelstationmanagerapp.retrofit;

import com.fuelstationmanagerapp.dbModel.FuelStation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    public static String BASE_URL = "http://192.168.1.110:4001/api/v1/";
    // as we are making a post request to post a data
    // so we are annotating it with post
    // and along with that we are passing a parameter as users
    @POST("fuelStations")

    //on below line we are creating a method to post our data.
    Call<FuelStation> createPost(@Body FuelStation dataModal);
}
