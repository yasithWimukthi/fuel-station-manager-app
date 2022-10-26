package com.fuelstationmanagerapp.dbModel;

public class StationStatus {

    private String name;
    private String owner;
    private String location;
    private String petrolStatus;
    private String petrolArrivalTime;
    private String petrolFinishedTime;
    private String dieselStatus;
    private String dieselArrivalTime;
    private String dieselFinishedTime;
    private String gasolineStatus;
    private String gasolineArrivalTime;
    private String gasolineFinishedTime;


    public StationStatus(String name, String owner, String location, String petrolStatus, String petrolArrivalTime, String petrolFinishedTime, String dieselStatus, String dieselArrivalTime, String dieselFinishedTime, String gasolineStatus, String gasolineArrivalTime, String gasolineFinishedTime) {
        this.name = name;
        this.owner = owner;
        this.location = location;
        this.petrolStatus = petrolStatus;
        this.petrolArrivalTime = petrolArrivalTime;
        this.petrolFinishedTime = petrolFinishedTime;
        this.dieselStatus = dieselStatus;
        this.dieselArrivalTime = dieselArrivalTime;
        this.dieselFinishedTime = dieselFinishedTime;
        this.gasolineStatus = gasolineStatus;
        this.gasolineArrivalTime = gasolineArrivalTime;
        this.gasolineFinishedTime = gasolineFinishedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPetrolStatus() {
        return petrolStatus;
    }

    public void setPetrolStatus(String petrolStatus) {
        this.petrolStatus = petrolStatus;
    }

    public String getPetrolArrivalTime() {
        return petrolArrivalTime;
    }

    public void setPetrolArrivalTime(String petrolArrivalTime) {
        this.petrolArrivalTime = petrolArrivalTime;
    }

    public String getPetrolFinishedTime() {
        return petrolFinishedTime;
    }

    public void setPetrolFinishedTime(String petrolFinishedTime) {
        this.petrolFinishedTime = petrolFinishedTime;
    }

    public String getDieselStatus() {
        return dieselStatus;
    }

    public void setDieselStatus(String dieselStatus) {
        this.dieselStatus = dieselStatus;
    }

    public String getDieselArrivalTime() {
        return dieselArrivalTime;
    }

    public void setDieselArrivalTime(String dieselArrivalTime) {
        this.dieselArrivalTime = dieselArrivalTime;
    }

    public String getDieselFinishedTime() {
        return dieselFinishedTime;
    }

    public void setDieselFinishedTime(String dieselFinishedTime) {
        this.dieselFinishedTime = dieselFinishedTime;
    }

    public String getGasolineStatus() {
        return gasolineStatus;
    }

    public void setGasolineStatus(String gasolineStatus) {
        this.gasolineStatus = gasolineStatus;
    }

    public String getGasolineArrivalTime() {
        return gasolineArrivalTime;
    }

    public void setGasolineArrivalTime(String gasolineArrivalTime) {
        this.gasolineArrivalTime = gasolineArrivalTime;
    }

    public String getGasolineFinishedTime() {
        return gasolineFinishedTime;
    }

    public void setGasolineFinishedTime(String gasolineFinishedTime) {
        this.gasolineFinishedTime = gasolineFinishedTime;
    }
}
