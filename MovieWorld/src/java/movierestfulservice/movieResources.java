/*
 * 
 */
package movierestfulservice;

import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author User
 */

@Named
@Path("/movies")
public class movieResources {
    
    @EJB
    private movieBean moviebean;
    
    public movieResources(){};
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllMovies(){
        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        List<Movie> allMovies = moviebean.getMovies();
        for(int i = 0; i < allMovies.size(); i++){
            buffer.append(allMovies.get(i).listJSONString());
            if(i != allMovies.size() - 1){
                buffer.append(", ");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
}
