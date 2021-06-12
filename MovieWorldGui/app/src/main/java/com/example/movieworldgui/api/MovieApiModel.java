package com.example.movieworldgui.api;

public class MovieApiModel {

    private String Name;
    private String Distributor;
    private String Genre;
    private int Year;

    public MovieApiModel() {
    }

    public MovieApiModel(String name) {
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    public String getDistributor() {
        return Distributor;
    }

    public String getGenre() {
        return Genre;
    }

    public int getYear() {
        return Year;
    }
}
