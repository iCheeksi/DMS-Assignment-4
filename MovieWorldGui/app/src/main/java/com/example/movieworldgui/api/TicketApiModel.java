package com.example.movieworldgui.api;

public class TicketApiModel {
    private String deviceId; //can be name or MAC address
    private String movieName;

    public TicketApiModel(){}

    public String getDeviceId() {
        return deviceId;
    }

    public String getMovieName() {
        return movieName;
    }
}
