/*
 * Instead of using SQL, putting all the movie details in this Detailed bean to retrieve later using @GET 
 */
package movierestfulservice;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 * Adding movie details with booking time available on singleton EJB. 
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
@Singleton
public class DetailedBean {
    private List<MovieDetails> detail;
    
    @PostConstruct
    public void listDetails(){
        detail = new ArrayList<>();
        addDetail("Godzilla VS Kong", 
                "Kong and his protectors undertake a perilous journey to find his true home. "
                        + "Along for the ride is Jia, an orphaned girl who has a unique and powerful bond with the mighty beast. "
                        + "However, they soon find themselves in the path of an enraged Godzilla as he cuts a swath of destruction across the globe. "
                        + "The initial confrontation between the two titans -- instigated by unseen forces -- is only the beginning of the mystery that lies deep within the core of the planet.",
                "18th June 2021 ** 7PM");
        addDetail("Mortal Kombat","Hunted by the fearsome warrior Sub-Zero, MMA fighter Cole Young finds sanctuary at the temple of Lord Raiden. "
                + "Training with experienced fighters Liu Kang, Kung Lao and the rogue mercenary Kano, "
                + "Cole prepares to stand with Earth's greatest champions to take on the enemies from Outworld in a high-stakes battle for the universe.",
                "18th June 2021 ** 9PM");
        addDetail("Stand by me","Gordie, Chris, Teddy and Vern are four friends who decide to hike to find the corpse of Ray Brower, "
                + "a local teenager, who was hit by a train while plucking blueberries in the wild.",
                "16th June 2021 ** 2.30PM");
        addDetail("Titanic","Seventeen-year-old Rose hails from an aristocratic family and is set to be married. "
                + "When she boards the Titanic, she meets Jack Dawson, an artist, and falls in love with him.",
                "15th June 2021 ** 1.45PM");
        addDetail("Good will hunting","Will Hunting, a genius in mathematics, solves all the difficult mathematical problems. "
                + "When he faces an emotional crisis, he takes help from psychiatrist Dr Sean Maguireto, who helps him recover.",
                "15th June 2021 ** 10AM");
        addDetail("Croods a new age","Searching for a safer habitat, the prehistoric Crood family discovers an idyllic, walled-in paradise that meets all of its needs. "
                + "Unfortunately, they must also learn to live with the Bettermans -- "
                + "a family that's a couple of steps above the Croods on the evolutionary ladder. "
                + "As tensions between the new neighbors start to rise, "
                + "a new threat soon propels both clans on an epic adventure that forces them to embrace their differences, "
                + "draw strength from one another, and survive together.",
                "17th June 2021 ** 11AM");
        addDetail("Hunt for the Wilderpeople","A boy (Julian Dennison) and his foster father (Sam Neill) become the subjects of a manhunt after they get stranded in the New Zealand wilderness.",
                "19th June 2021 ** 7.15PM");
    }
    
    public void addDetail(String movieName, String movieSummary, String movieDate){
        detail.add(new MovieDetails(movieName, movieSummary, movieDate));
    }
    
    public List<MovieDetails> getMovieDetailsForMovie(String movie){
        List<MovieDetails> dits = new ArrayList<>();
        for(MovieDetails dit : detail){
            if(dit.getMovieName().equals(movie)){
                dits.add(dit);
            }
        }
        return dits;
    }
}
