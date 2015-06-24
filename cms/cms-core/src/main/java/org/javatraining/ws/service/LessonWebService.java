package org.javatraining.ws.service;

import org.javatraining.auth.Auth;
import org.javatraining.config.AuthRole;
import org.javatraining.model.LessonVO;
import org.javatraining.service.LessonService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by asudak on 6/24/15.
 */
@Path("course")
public class LessonWebService {
    @EJB
    LessonService lessonService;

    @GET
    @Path("{course_id}/lesson")
    @Produces(MediaType.APPLICATION_JSON)
    @Auth(roles = {AuthRole.STUDENT, AuthRole.TEACHER})
    public Response getLessons(@PathParam("course_id") Long courseId) {
        return Response.ok(lessonService.getByCourseId(courseId)).build();
    }

    @POST
    @Path("{course_id}/lesson")
    @Consumes(MediaType.APPLICATION_JSON)
    @Auth(roles = {AuthRole.TEACHER})
    public Response saveLesson(@Context UriInfo uriInfo, @PathParam("course_id") Long courseId, LessonVO lesson) {
        Response.ResponseBuilder r;
        try {
            lesson.setCourseId(courseId);
            lesson.setId(null); //make sure that there no id set.

            lessonService.save(lesson);

            String lessonUri = uriInfo.getRequestUri().toString() + "/" + lesson.getId();
            r = Response.created(new URI(lessonUri));
        } catch (NullPointerException e) {
            //seems Lesson wasn't saved
            r = Response.serverError(); //FIXME find error code that will describe error better
        } catch (URISyntaxException e) {
            //this shouldn't happen
            r = Response.serverError();
        }
        return r.build();
    }
}
