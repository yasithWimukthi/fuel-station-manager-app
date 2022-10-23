package com.fuelstationmanagerapp.model;

public class FuelStation {
    private String name;
    private boolean petrolStatus;
    private boolean dieselStatus;
    private boolean gasolineStatus;

    public FuelStation(String name, boolean petrolStatus, boolean dieselStatus, boolean gasolineStatus) {
        this.name = name;
        this.petrolStatus = petrolStatus;
        this.dieselStatus = dieselStatus;
        this.gasolineStatus = gasolineStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPetrolStatus() {
        return petrolStatus;
    }

    public void setPetrolStatus(boolean petrolStatus) {
        this.petrolStatus = petrolStatus;
    }

    public boolean isDieselStatus() {
        return dieselStatus;
    }

    public void setDieselStatus(boolean dieselStatus) {
        this.dieselStatus = dieselStatus;
    }

    public boolean isGasolineStatus() {
        return gasolineStatus;
    }

    public void setGasolineStatus(boolean gasolineStatus) {
        this.gasolineStatus = gasolineStatus;
    }
}
