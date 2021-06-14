/*
 * Class to hold booked tickets. 
 */
package movierestfulservice;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
@Stateless
public class bookTicketBean {
    
    //Grab MovieBean
    @EJB
    private movieBean MovieBean;
    
    //Grab ticketbean
    @EJB
    private ticketBean TicketBean;
    
    //Method to check if the booking ticket is already on the list, if not add
    public void BookTicket(String movieName){
        Movie movie = MovieBean.getMovie(movieName);
        boolean ticketBooked = false;
        for(Movie movies : TicketBean.getTicket()){
            if(movies.getName().equalsIgnoreCase(movieName)){
                ticketBooked = true;
            }
        }
        if(!ticketBooked){
            TicketBean.addTicket(movie);
        }
    }
    
    public void SaveTicket(Ticket ticket){
        TicketBean.saveTicket(ticket);
    }
}
