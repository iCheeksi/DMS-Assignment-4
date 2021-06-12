package com.example.movieworldgui.api;

import java.io.Serializable;

public class TicketApiModel implements Serializable {

    private String Id;
    private String DeviceID; //can be name or MAC address
    private String Moviename;
    private String MovieDate;
    private String Duration;
    private String SeatNumber;

    public TicketApiModel(){}

    public TicketApiModel(String deviceId, String movieName){
        DeviceID = deviceId;
        Moviename = movieName;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public String getMoviename() {
        return Moviename;
    }
}
