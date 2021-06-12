/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierestfulservice;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class bookTicketBean {
    
    @EJB
    private movieBean MovieBean;
    @EJB
    private ticketBean TicketBean;
    
    public void BookTicket(String movieName){
        Movie movie = MovieBean.getMovie(movieName);
        boolean ticketBooked = false;
        for(Movie movies : TicketBean.getTicket()){
            if(movie.getName().equalsIgnoreCase(movieName)){
                ticketBooked = true;
            }
        }
        if(!ticketBooked){
            TicketBean.addTicket(movie);
        }
    }
}
