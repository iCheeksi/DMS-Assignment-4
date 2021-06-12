package com.example.movieworldgui.api;

public class TicketApiModel {
    private String DeviceId; //can be name or MAC address
    private String MovieName;

    public TicketApiModel(){}

    public String getDeviceId() {
        return DeviceId;
    }

    public String getMovieName() {
        return MovieName;
    }
}
