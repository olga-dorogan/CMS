package org.javatraining.ws.services;

import flexjson.JSONException;
import org.javatraining.model.CourseVO;
import org.javatraining.model.PersonVO;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asudak on 04.06.15.
 */
@Stateless
@Path("course")
public class CourseService extends AbstractService<CourseVO> {
    CourseService() {
        super(CourseVO.class);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseList() {
        List<CourseVO> courses = new ArrayList<>();
        //TODO get list of courses here
        return Response.ok(serialize(courses)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{course_id}")
    public Response getCourse(@PathParam("course_id") long courseId) {
        CourseVO course = new CourseVO();
        course.setId(courseId);
        //TODO get course from DB
        Response r;
        if (course == null)
            r = Response.noContent().build();
        else
            r = Response.ok(serialize(course)).build();
        return r;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCourse(@Context UriInfo uriInfo, @QueryParam("course_json") String courseJson) {
        Response r;
        try {
            CourseVO course = deserialize(courseJson);
            //TODO save entity here
            String courseUri = uriInfo.getRequestUri().toString() + "/" + course.getId();
            r = Response.created(new URI(courseUri)).build();
        } catch (JSONException e) {
            System.out.println(e);
            r = Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (URISyntaxException e) {
            //this shouldn't happen
            r = Response.serverError().build();
        }

        return r;
    }

    @DELETE
    @Path("{course_id}")
    public Response deleteCourse(@PathParam("course_id") long courseId) {
        Response r;
        CourseVO course = new CourseVO();
        course.setId(courseId);
        try {
            //TODO delete entity here
            r = Response.ok().build();
        } catch (Exception e) {
            r = Response.noContent().build();
        }
        return r;
    }

    @GET
    @Path("{course_id}/subscribers")
    public Response getSubscribers(@PathParam("course_id") long courseId) {
        CourseVO course = new CourseVO();
        course.setId(courseId);
        List<PersonVO> persons = new ArrayList<>();
        //TODO subscribe person to course
        return Response.ok(serialize(persons)).build();
    }

    @PUT
    @Path("{course_id}/subscribe")
    public Response subscribeCourse(@PathParam("course_id") long courseId, @QueryParam("person_id") long personId) {
        PersonVO person = new PersonVO();
        person.setId(personId);
        CourseVO course = new CourseVO();
        course.setId(courseId);
        //TODO subscribe person to course
        return Response.accepted().build();
    }

    @PUT
    @Path("{course_id}/unsubscribe")
    public Response unsubscribeCourse(@PathParam("course_id") long courseId, @QueryParam("person_id") long personId) {
        PersonVO person = new PersonVO();
        person.setId(personId);
        CourseVO course = new CourseVO();
        course.setId(courseId);
        //TODO unsubscribe person to course
        return Response.accepted().build();
    }
}
