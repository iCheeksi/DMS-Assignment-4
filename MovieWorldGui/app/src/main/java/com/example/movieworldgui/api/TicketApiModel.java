package com.example.movieworldgui.api;

public class TicketApiModel {
    private int Id;
    private String DeviceId; //can be name or MAC address
    private String MovieName;
    private String MovieDate;
    private String Duration;
    private String SeatNumber;

    public TicketApiModel(){}

    public TicketApiModel(String deviceId, String movieName){
        DeviceId = deviceId;
        MovieName = movieName;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public String getMovieName() {
        return MovieName;
    }
}
