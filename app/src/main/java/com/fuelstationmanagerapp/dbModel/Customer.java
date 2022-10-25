package com.fuelstationmanagerapp.dbModel;

public class Customer {
    private String _id;
    private String customerName;
    private String arrivalTime;
    private String departTime;
    private String status;

    public Customer(String _id, String customerName, String arrivalTime, String departTime, String status) {
        this._id = _id;
        this.customerName = customerName;
        this.arrivalTime = arrivalTime;
        this.departTime = departTime;
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public String getCustomerName() {
        return customerName;
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
