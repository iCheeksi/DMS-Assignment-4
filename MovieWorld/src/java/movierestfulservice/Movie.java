/*
 * Movie information. Grab and set movie information in this class
 */
package movierestfulservice;

/**
 *
 * @author User
 */
public class Movie {
    private String name, distributor, genre;
    private int year;
    
    public Movie(){};
    
    public Movie(String mName, String mDistributor, String mGenre, int mYear){
        name = mName;
        distributor = mDistributor;
        genre = mGenre;
        year = mYear;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getDistributor(){
        return distributor;
    }
    
    public void setDistributor(String distributor){
        this.distributor = distributor;
    }
    
    public String getGenre(){
        return genre;
    }
    
    public void setGenre(String genre){
        this.genre = genre;
    }
    
    public int getYear(){
        return year;
    }
    
    public void setYear(int year){
        this.year = year;
    } 
    
    public String listJSONString(){
        StringBuilder buffer = new StringBuilder();
        buffer.append("{ \"name\":\"").append(this.getName()).append("\", ");
        buffer.append("{ \"distributor\":\"").append(this.getDistributor()).append("\", ");
        buffer.append("{ \"genre\":\"").append(this.getGenre()).append("\", ");
        buffer.append("{ \"year\":\"").append(this.getYear()).append("\", ");
        
        return buffer.toString();
    }
}
