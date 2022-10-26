package com.fuelstationmanagerapp.dbModel;

public class SingleQueueObject {
    private String _id;
    private String fuelStation;
    private String fuelTypeName;
    private String customerName;
    private String vehicleType;
    private String arrivalTime;
    private String departTime;
    private String status;

    public SingleQueueObject() {

    }

    public SingleQueueObject(String _id, String fuelStation, String fuelTypeName, String customerName, String vehicleType, String arrivalTime, String departTime, String status) {
        this._id = _id;
        this.fuelStation = fuelStation;
        this.fuelTypeName = fuelTypeName;
        this.customerName = customerName;
        this.vehicleType = vehicleType;
        this.arrivalTime = arrivalTime;
        this.departTime = departTime;
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setFuelStation(String fuelStation) {
        this.fuelStation = fuelStation;
    }

    public void setFuelTypeName(String fuelTypeName) {
        this.fuelTypeName = fuelTypeName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFuelStation() {
        return fuelStation;
    }

    public String getFuelTypeName() {
        return fuelTypeName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartTime() {
        return departTime;
    }

    public String getStatus() {
        return status;
    }
}
