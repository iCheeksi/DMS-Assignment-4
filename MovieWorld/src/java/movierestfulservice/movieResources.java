/*
 * 
 */
package movierestfulservice;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
@Named
@Path("/movies")
public class movieResources {

    @EJB
    private movieBean Moviebean;
    @EJB
    private ticketBean TicketBean;
    @EJB
    private bookTicketBean BookTicketBean;

    public movieResources() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllMovies() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        List<Movie> allMovies = Moviebean.getMovies();
        for (int i = 0; i < allMovies.size(); i++) {
            buffer.append(allMovies.get(i).listJSONString());
            if (i != allMovies.size() - 1) {
                buffer.append(", ");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    @POST
    @Path("/ticket")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addTicket(MultivaluedMap<String, String> formParams) {
        String movieName = formParams.getFirst("Ticket");
        BookTicketBean.BookTicket(movieName);
    }

    @POST
    @Path("/ticket")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addTicket(String json) {

        JsonElement elem = JsonParser.parseString(json);
        Ticket ticket = new Gson().fromJson(elem, Ticket.class);
        BookTicketBean.SaveTicket(ticket);
    }

    @POST
    @Path("/ticket/delete/{id}")
    public void deleteTicket(@PathParam("id") String id) {

        List<Ticket> tickets = TicketBean.getSavedTickets();
        tickets.removeIf(t -> t.getID().equalsIgnoreCase(id));
    }

    @GET
    @Path("/ticket")
    @Produces(MediaType.APPLICATION_JSON)
    public String listBookedTickets() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        List<Movie> listMovies = TicketBean.getTicket();
        for (int i = 0; i < listMovies.size(); i++) {
            buffer.append(listMovies.get(i).listJSONString());
            if (i != listMovies.size() - 1) {
                buffer.append(",");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    @GET
    @Path("/ticket/{deviceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String filterTickets(@PathParam("deviceId") String deviceId) {
        StringBuilder buffer = new StringBuilder();

        List<Ticket> tickets = TicketBean.getSavedTickets();
        ArrayList<Ticket> filtered = new ArrayList<>(tickets);
        filtered.removeIf(t -> !t.getDeviceID().equalsIgnoreCase(deviceId));

        buffer.append("[");
        for (int i = 0; i < filtered.size(); i++) {
            buffer.append(filtered.get(i).listJsonString());
            if (i != filtered.size() - 1) {
                buffer.append(",");
            }
        }
        buffer.append("]");
        
        return buffer.toString();
    }
}
