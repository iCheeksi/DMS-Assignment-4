/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierestfulservice;

/**
 *
 * @author Shelby Mun 19049176
 */
public class MovieDetails {
    private String movieName, movieSummary, movieDate;
    
    public MovieDetails(){}
    
    public MovieDetails(String MovieName,String MovieSummary, String MovieDate){
        movieName = MovieName;
        movieSummary = MovieSummary;
        movieDate = MovieDate;
    }
    
    public String getMovieName(){
        return this.movieName;
    }
    
    public void setMovieName(String MovieName){
        movieName = MovieName;
    }
    
    public String getMovieSummary(){
        return this.movieSummary;
    }
    
    public void setMovieSummary(String MovieSummary){
        movieSummary = MovieSummary;
    }
    
    public String getMovieDate(){
        return this.movieDate;
    }
    
    public void setMovieDate(String movieDate){
        this.movieDate = movieDate;
    }
    
    public String listJsonString(){
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("{ \"MovieName\":\"").append(this.getMovieName()).append("\", ");
        buffer.append(" \"Summary\":\"").append(this.getMovieSummary()).append("\", ");
        buffer.append(" \"Date\":\"").append(this.getMovieDate()).append("\" } ");
        return buffer.toString();
    }
}
