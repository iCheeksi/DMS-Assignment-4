/*
 * JSON media type. List of movies are available here
 */
package movierestfulservice;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 *
 * @author User
 */

@Singleton
public class movieBean {
    private List<Movie> movies;
    
    @PostConstruct
    public void listOfMovies(){
        movies = new ArrayList<>();
        addMovie("Godzilla VS Kong", "Warner Bros", "Action", 2021);
        addMovie("Mortal Kombat", "Warner Bros", "Action", 2021);
        addMovie("Stand by me", "Columbia Pictures", "Drama", 1986);
        addMovie("Titanic","20th Century Studios","Drama", 1997);
        addMovie("Good will hunting", "Miramax", "Romance", 1997);
        addMovie("Croods a new age","Universal Pictures","Animation", 2020);
        addMovie("Hunt for the Wilderpeople", "Madman Entertainment", "Comedy", 2016);   
    }
    
    public void addMovie(String mName, String mDistributor, String mGenre, int mYear){
        movies.add(new Movie(mName, mDistributor,mGenre,mYear));
    }
    
    public List<Movie> getMovies(){
        return movies;
    }
    
    public Movie getMovie(String mName){
        Movie viewMovie = null;
        for (Movie movie : movies){
            if(movie.getName().equalsIgnoreCase(mName)){
                viewMovie = movie;
            }
        }
        return viewMovie;
    }
}
