/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierestfulservice;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
@Singleton
public class ticketBean {
    private List<Movie> tickets;
    private List<Ticket> savedTickets;
    
    @PostConstruct
    public void bookTickets(){
        tickets = new ArrayList<>();
        savedTickets = new ArrayList<>();
    }
    
    public void addTicket(Movie movie){
        tickets.add(movie);
    }
    
    public void saveTicket(Ticket ticket){
        savedTickets.add(ticket);
    }
    
    public List<Movie> getTicket(){
        return tickets;
    }
      
    public List<Ticket> getSavedTickets(){
        return savedTickets;
    }
}
