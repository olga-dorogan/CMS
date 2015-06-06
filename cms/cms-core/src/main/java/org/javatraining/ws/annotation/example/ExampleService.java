package org.javatraining.ws.annotation.example;

import org.javatraining.auth.Auth;
import org.javatraining.config.AuthRole;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by olga on 05.06.15.
 */
@Path("example")
public class ExampleService {
    @GET
    public Response getExample() {
        return Response.ok().build();
    }

    @GET
    @Path("/teacher/{id}")
    @Auth(roles = {AuthRole.TEACHER})
    public Response getTeacherInfo() {
        return Response.ok().build();
    }

    @GET
    @Path("/student/{id}")
    @Auth(roles = {AuthRole.STUDENT})
    public Response getStudentInfo() {
        return Response.ok().build();
    }
}
