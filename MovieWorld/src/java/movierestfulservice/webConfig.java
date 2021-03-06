/*
    Path to RESTful web services
 */
package movierestfulservice;

import java.util.Set;
import javax.ws.rs.core.Application;

/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
@javax.ws.rs.ApplicationPath("movieworldservice")
public class webConfig extends Application{
    
    @Override
    public Set<Class<?>> getClasses(){
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(movierestfulservice.MovieDetailsResource.class);
        resources.add(movierestfulservice.movieResources.class);
    }
}
