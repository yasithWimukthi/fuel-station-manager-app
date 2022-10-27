package com.fuelstationmanagerapp.dbModel;

import com.google.gson.annotations.SerializedName;

public class FuelStation {

    private String owner;
    private String name;
    private String location;


    public FuelStation(String owner, String name, String location) {
        this.owner = owner;
        this.name = name;
        this.location = location;

    }
    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getLocation() {
        return location;
    }
}