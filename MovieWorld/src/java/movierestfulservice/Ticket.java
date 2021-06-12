/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierestfulservice;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class Ticket implements Serializable {
    private String Id, Moviename, DeviceID, MovieDate, Duration, SeatNumber;
    
    public Ticket(){}
    
    public Ticket(String id,String movieName, String deviceID, String movieDate, String duration, String seating){
        Id = id;
        Moviename = movieName;
        DeviceID = deviceID;
        MovieDate = movieDate;
        Duration = duration;
        SeatNumber = seating;
    }
    
    public void setMovieName(String movieName){
        this.Moviename = movieName;
    }
    
    public String getMovieName(){
        return this.Moviename;
    }
    
    public void setDeviceID(String deviceID){
        this.DeviceID = deviceID;
    }
    
    public String getID(){
        return this.Id;
    }
    
    public String getDeviceID(){
        return this.DeviceID;
    }
    
    public void setMovieDate(String movieDate){
        this.MovieDate = movieDate;
    }
    
    public String getMovieDate(){
        return this.MovieDate;
    }
    
    public void setDuration(String duration){
        this.Duration = duration;
    }
    
    public String getDuration(){
        return this.Duration;
    }
    
    public void setSeating(String seating){
        this.SeatNumber = seating;
    }
    
    public String getSeating(){
        return this.SeatNumber;
    }
    
    public String listJsonString(){
        StringBuilder buffer = new StringBuilder();
        buffer.append("\"Id\":\"").append(this.getID()).append("\",");
        buffer.append("\"MovieName\":\"").append(this.getMovieName()).append("\",");
        buffer.append("\"DeviceID\":\"").append(this.getDeviceID()).append("\",");
        buffer.append("\"MovieDate\":\"").append(this.getMovieDate()).append("\",");
        buffer.append("\"Duration\":\"").append(this.getDuration()).append("\",");
        buffer.append("\"SeatNumber\":\"").append(this.getSeating()).append("\"}");
        return buffer.toString();
    }
}
