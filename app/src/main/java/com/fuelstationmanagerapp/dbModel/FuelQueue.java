package com.fuelstationmanagerapp.dbModel;

import java.util.ArrayList;

public class FuelQueue {

    private String fuelStationId;
    private String fuelStationName;
    private String fuelType;
    private String vehicleType;
    private String fuelStatus;
    private int count;
    private ArrayList<Customer> customers;

    public FuelQueue(String fuelStationId) {
        this.fuelStationId = fuelStationId;
    }

    public FuelQueue(String fuelStationId, String fuelStationName, String fuelType, String vehicleType, String fuelStatus, int count, ArrayList<Customer> customers) {
        this.fuelStationId = fuelStationId;
        this.fuelStationName = fuelStationName;
        this.fuelType = fuelType;
        this.vehicleType = vehicleType;
        this.fuelStatus = fuelStatus;
        this.count = count;
        this.customers = customers;
    }

    public String getFuelStationId() {
        return fuelStationId;
    }

    public String getFuelStationName() {
        return fuelStationName;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getFuelStatus() {
        return fuelStatus;
    }

    public int getCount() {
        return count;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }
}
