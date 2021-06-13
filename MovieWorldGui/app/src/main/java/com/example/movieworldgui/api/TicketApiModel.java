package com.example.movieworldgui.api;

import java.io.Serializable;

/***
 *  Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
public class TicketApiModel implements Serializable {

    private String Id;
    private String DeviceID;
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
