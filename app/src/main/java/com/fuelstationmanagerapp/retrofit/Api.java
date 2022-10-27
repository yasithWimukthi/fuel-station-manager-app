package com.fuelstationmanagerapp.retrofit;

import com.fuelstationmanagerapp.dbModel.FuelQueue;
import com.fuelstationmanagerapp.dbModel.FuelStation;
import com.fuelstationmanagerapp.dbModel.NameObj;
import com.fuelstationmanagerapp.dbModel.SingleQueueObject;
import com.fuelstationmanagerapp.dbModel.StationStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    public static String BASE_URL = "http://192.168.1.10:4001/api/v1/";

    //API method to create fuel station
    @POST("fuelStations")
    Call<FuelStation> createFuelStation(@Body FuelStation dataModal);

    //API method to get all fuel stations
    @GET("fuelStations")
    Call<List<FuelStation>> getFuelStations();

    //API method to get fuel stations by owner name
    @GET("fuelStations/owner")
    Call<List<StationStatus>> getFuelStationsByOwner(@Query("owner") String owner);

    //API method to set fuel Status
    @POST("fuelStations/addStatus/{id}")
    Call<StationStatus> setFuelStatus(@Path("id") String id, @Body StationStatus stationStatus);

    //API method to get fuel queue
    @GET("fuelQueues/getQueue")
    Call<FuelQueue> getFuelQueues(@Query("stationName") String stationName, @Query("vehicleType") String vehicleType,
                                  @Query("fuelType") String fuelType);

    //API method to join fuel queue
    @POST("fuelQueues")
    Call<SingleQueueObject> joinTheQueue(@Body SingleQueueObject model);

    //API method to exit before pump
    @POST("fuelQueues/exitBeforePump")
    Call<SingleQueueObject> exitBeforePump(@Body NameObj nameObj);

    //API method to exit after pump
    @POST("fuelQueues/exitAfterPump")
    Call<SingleQueueObject> exitAfterPump(@Body NameObj nameObj);
}
