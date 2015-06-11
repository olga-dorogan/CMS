package org.javatraining.ws.services;

import flexjson.JSONException;
import org.javatraining.auth.Auth;
import org.javatraining.config.AuthRole;
import org.javatraining.config.Config;
import org.javatraining.entity.PersonRole;
import org.javatraining.model.CourseVO;
import org.javatraining.model.PersonVO;
import org.javatraining.service.CourseService;
import org.javatraining.service.PersonService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by asudak on 04.06.15.
 */
@Stateless
@Path("course")
public class CourseWebService extends AbstractWebService<CourseVO> {
    @EJB
    private CourseService courseService;
    @EJB
    private PersonService personService;

    CourseWebService() {
        super(CourseVO.class);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseList() {
        List<CourseVO> courses = courseService.getAll();
        return Response.ok(serialize(courses)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{course_id}")
    public Response getCourse(@PathParam("course_id") long courseId) {
        CourseVO course =courseService.getById(courseId);
        Response.ResponseBuilder r;
        if (course == null)
            r = Response.noContent();
        else
            r = Response.ok(serialize(course));
        return r.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Auth(roles = {AuthRole.TEACHER})
    public Response createCourse(@Context UriInfo uriInfo, @QueryParam("course_json") String courseJson) {
        Response.ResponseBuilder r;
        try {
            CourseVO course = deserialize(courseJson);
            courseService.save(course);
            String courseUri = uriInfo.getRequestUri().toString() + "/" + course.getId();
            r = Response.created(new URI(courseUri));
        } catch (JSONException e) {
            System.out.println(e);
            r = Response.status(Response.Status.NOT_ACCEPTABLE);
        } catch (URISyntaxException e) {
            //this shouldn't happen
            r = Response.serverError();
        }

        return r.build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Auth(roles = {AuthRole.TEACHER})
    public Response updateCourse( @QueryParam("course_json") String courseJson) {
        Response.ResponseBuilder r;
        try {
            CourseVO course = deserialize(courseJson);
            courseService.update(course);
            r = Response.ok();
        } catch (JSONException e) {
            System.out.println(e);
            r = Response.status(Response.Status.NOT_ACCEPTABLE);
        }

        return r.build();
    }

    @DELETE
    @Path("{course_id}")
    @Auth(roles = {AuthRole.TEACHER})
    public Response deleteCourse(@PathParam("course_id") long courseId) {
        Response.ResponseBuilder r;
        CourseVO course = new CourseVO();
        course.setId(courseId);
        try {
            courseService.remove(course);
            r = Response.ok();
        } catch (Exception e) {
            r = Response.noContent();
        }
        return r.build();
    }

    @GET
    @Path("{course_id}/subscribers")
    @Auth(roles = {AuthRole.TEACHER})
    public Response getSubscribers(@PathParam("course_id") long courseId) {
        CourseVO course = new CourseVO();
        course.setId(courseId);
        List<PersonVO> persons = courseService.getAllPersonsFromCourseByRole(course, PersonRole.STUDENT);
        return Response.ok(serialize(persons)).build();
    }

    @PUT
    @Path("{course_id}/subscribe")
    @Auth(roles = {AuthRole.STUDENT})
    public Response subscribeCourse(@PathParam("course_id") long courseId, @QueryParam("person_id") long personId) {
        PersonVO person = new PersonVO();
        person.setId(personId);
        CourseVO course = new CourseVO();
        course.setId(courseId);
        personService.addPersonToCourse(person, course);
        return Response.accepted().build();
    }

    @PUT
    @Path("{course_id}/unsubscribe")
    @Auth(roles = {AuthRole.STUDENT})
    public Response unsubscribeCourse(@PathParam("course_id") long courseId, @HeaderParam(Config.REQUEST_HEADER_ID) long userId) {
        PersonVO person = new PersonVO();
        person.setId(userId);
        CourseVO course = new CourseVO();
        course.setId(courseId);
        personService.removePersonFromCourse(person, course);
        return Response.accepted().build();
    }
}
