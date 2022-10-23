package com.fuelstationmanagerapp.model;

import com.google.gson.annotations.SerializedName;

public class FuelStation {

    @SerializedName("name")
    private String superName;

    @SerializedName("owner")
    private String superOwner;

    @SerializedName("location")
    private String superLocation;


    public FuelStation(String name, String owner, String location) {
        this.superName = name;
        this.superOwner = owner;
        this.superLocation = location;

    }

    public String getName() {
        return superName;
    }

    public String getOwner() {
        return superOwner;
    }

    public String getLocation() {
        return superLocation;
    }
}
