package com.example.movieworldgui.api;

public class TicketApiModel {

    private String Id;
    private String DeviceID; //can be name or MAC address
    private String MovieName;
    private String MovieDate;
    private String Duration;
    private String SeatNumber;

    public TicketApiModel() {
    }

    public TicketApiModel(String id, String deviceId, String movieName) {
        Id = id;
        DeviceID = deviceId;
        MovieName = movieName;
    }

    public TicketApiModel(String name){
        MovieName = name;
    }

    public String getID() { return Id; }

    public String getDeviceID() {
        return DeviceID;
    }

    public String getMovieName() {
        return MovieName;
    }
}
