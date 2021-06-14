/*
 * Class to set and get movie details 
 */
package movierestfulservice;

/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
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
    
    //Grabbing details and setting in JSON format.
    public String listJsonString(){
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("{ \"MovieName\":\"").append(this.getMovieName()).append("\", ");
        buffer.append(" \"Summary\":\"").append(this.getMovieSummary()).append("\", ");
        buffer.append(" \"Date\":\"").append(this.getMovieDate()).append("\" } ");
        return buffer.toString();
    }
}
