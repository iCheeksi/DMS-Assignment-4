/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierestfulservice;

import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
@Named
@Path("/details")
public class MovieDetailsResource {
    
    @EJB
    private DetailedBean db;
    
    public MovieDetailsResource(){}
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{movie}")
    public String getMovieDetails(@PathParam("movie")String movieName){
        StringBuilder buffer = new StringBuilder();
        List<MovieDetails> details = db.getMovieDetailsForMovie(movieName);
        for(int i = 0; i < details.size(); i++){
            buffer.append(details.get(i).listJsonString());
            if(i != details.size() -1){
                buffer.append(", ");
            }
        }
        return buffer.toString();
    }
}
