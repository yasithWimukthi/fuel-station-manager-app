package com.fuelstationmanagerapp.model;

import com.google.gson.annotations.SerializedName;

public class FuelStation {

    private String name;
    private String owner;
    private String location;


    public FuelStation(String name, String owner, String location) {
        this.name = name;
        this.owner = owner;
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
