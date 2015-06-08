package org.javatraining.ws.config;

import org.javatraining.ws.services.PersonService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by asudak on 5/29/15.
 */
@ApplicationPath("/resources")
public class GenericServiceConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet<>();
        classes.add(PersonService.class);
        return classes;
    }
}
