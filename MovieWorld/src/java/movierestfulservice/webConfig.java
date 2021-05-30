/*
    Path to RESTful web services
 */
package movierestfulservice;

import java.util.Set;
import javax.ws.rs.core.Application;
/**
 *
 * @author User
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
        resources.add(movierestfulservice.movieResources.class);
    }
}
