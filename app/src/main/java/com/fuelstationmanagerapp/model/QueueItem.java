package com.fuelstationmanagerapp.model;

public class QueueItem {
    private String customerName;
    private String status;
    private String arrivalTime;
    private String departTime;


    public QueueItem(String customerName, String status, String arrivalTime, String departTime) {
        this.customerName = customerName;
        this.status = status;
        this.arrivalTime = arrivalTime;
        this.departTime = departTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getStatus() {
        return status;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartTime() {
        return departTime;
    }
}
