package com.fuelstationmanagerapp.retrofit;

import com.fuelstationmanagerapp.model.FuelStation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    // as we are making a post request to post a data
    // so we are annotating it with post
    // and along with that we are passing a parameter as users
    @POST("fuelStations")

    //on below line we are creating a method to post our data.
    Call<FuelStation> createPost(@Body FuelStation dataModal);
}
