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

/**
 *
 * @author Shelby Mun 19049176
 */
@Singleton
public class ticketBean {
    private List<Movie> ticket;
    
    @PostConstruct
    public void bookTickets(){
        ticket = new ArrayList<>();
    }
    
    public void addTicket(Movie movie){
        ticket.add(movie);
    }
    
    public List<Movie> getTicket(){
        return ticket;
    }
}
