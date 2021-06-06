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

/**
 *
 * @author Shelby Mun 19049176
 */

@Named
@Path("/details")
public class MovieDetailsResource {
    
    @EJB
    private DetailedBean db;
    
    public MovieDetailsResource(){}
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{Movie}")
    public String getMovieDetails(@PathParam("Movie")String movieName){
        StringBuilder buffer = new StringBuilder();
        buffer.append("[ ");
        List<MovieDetails> details = DetailedBean.getMovieDetailsForMovie(movieName);
        for(int i = 0; i < details.size(); i++){
            buffer.append(details.get(i).listJsonString());
        }
    }
}